package io.github.blaney83;

import java.io.File;
import java.io.IOException;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnDomainCreator;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowKey;
import org.knime.core.data.container.CellFactory;
import org.knime.core.data.container.ColumnRearranger;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.util.dialog.field.ColumnField;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * This is the model implementation of DynamicColumnGenerator. A dynamic column
 * generator for appending columns to tables to assist with workflow testing,
 * visualization techniques and general validation.
 *
 * @author Ben Laney
 */
public class DynamicColumnGeneratorNodeModel extends NodeModel {

	// the logger instance
	private static final NodeLogger logger = NodeLogger.getLogger(DynamicColumnGeneratorNodeModel.class);

	/**
	 * the settings key which is used to retrieve and store the settings (from the
	 * dialog or from a settings file) (package visibility to be usable from the
	 * dialog).
	 */
	public static final int IN_PORT = 0;

	public static final String CFGKEY_NEW_COLUMN_NAME = "columnName";
	// this option will be provided in the form of a dialog check box,
	// true being appended column will be of type string, false (default)
	// being column of type double
	public static final String CFGKEY_STRING_COLUMN = "stringColumn";

	// Internal Model Keys
	private static final String FILE_NAME = "dynamicColumnGenerator.xml";

	private static final String INTERNAL_MODEL = "internalModel";

	// setings model and defaults
	private final SettingsModelString m_columnName = new SettingsModelString(
			DynamicColumnGeneratorNodeModel.CFGKEY_NEW_COLUMN_NAME, "");

	private final SettingsModelBoolean m_columnType = new SettingsModelBoolean(
			DynamicColumnGeneratorNodeModel.CFGKEY_STRING_COLUMN, false);

	// constructor
	protected DynamicColumnGeneratorNodeModel() {
		super(1, 1);
	}

	@Override
	protected BufferedDataTable[] execute(final BufferedDataTable[] inData, final ExecutionContext exec)
			throws Exception {
		DataColumnSpec colSpec = inData[IN_PORT].getDataTableSpec().getColumnSpec(m_columnName.getStringValue());
		//would check domain and range here, create our model, etc. but due to the simplicity of this node and its
		//current lack of a view, there is negligible information to store in our external model (which would be
		//primarily for loading the saved view, if there was one)
		CellFactory cellFactory = new DynamicColumnGeneratorCellFactory(createOutputColumnSpec(), m_columnType.getBooleanValue());
		ColumnRearranger outputTable = new ColumnRearranger(inData[IN_PORT].getDataTableSpec());
		outputTable.append(cellFactory);
		
		BufferedDataTable bufferedOutput = exec.createColumnRearrangeTable(inData[IN_PORT], outputTable, exec);
		return new BufferedDataTable[] {bufferedOutput};
	}

	@Override
	protected void reset() {

	}

	@Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
        boolean hasSomeDimensions = false;     
        try {
        	if(inSpecs[IN_PORT].getNumColumns() > 0) {
        		hasSomeDimensions = true;
        	}
        	if(!hasSomeDimensions) {
        		throw new InvalidSettingsException("Table must have at least one existing column.");
        	}
        	DataColumnSpec newColumnSpec = createOutputColumnSpec();
        	DataTableSpec appendSpec = new DataTableSpec(newColumnSpec);
        	DataTableSpec outputSpec = new DataTableSpec(inSpecs[IN_PORT], appendSpec);
        	return new DataTableSpec[] {outputSpec};
        	
        }catch (NullPointerException e){
        	throw new NullPointerException("You cannot provide an empty table to this node.");
        }
    }
	
	private DataColumnSpec createOutputColumnSpec() {
		DataColumnSpecCreator colSpecCreator = new DataColumnSpecCreator(m_columnName.getStringValue(), DoubleCell.TYPE);
		DataColumnDomainCreator domainCreator = new DataColumnDomainCreator(new DoubleCell(Double.MIN_VALUE), new DoubleCell(Double.MAX_VALUE));
		if(m_columnType.getBooleanValue()) {
			colSpecCreator = new DataColumnSpecCreator(m_columnName.getStringValue(), StringCell.TYPE);
			domainCreator = new DataColumnDomainCreator();
		}
		colSpecCreator.setDomain(domainCreator.createDomain());
		DataColumnSpec newColumnSpec = colSpecCreator.createSpec();
		return newColumnSpec;
	}

	@Override
	protected void saveSettingsTo(final NodeSettingsWO settings) {
		m_columnName.saveSettingsTo(settings);
		m_columnType.saveSettingsTo(settings);
	}

	@Override
	protected void loadValidatedSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
		// It can be safely assumed that the settings are already validated at this point
		m_columnName.loadSettingsFrom(settings);
		m_columnType.loadSettingsFrom(settings);
	}

	@Override
	protected void validateSettings(final NodeSettingsRO settings) throws InvalidSettingsException {
		m_columnName.validateSettings(settings);
		m_columnType.validateSettings(settings);
	}

	@Override
	protected void loadInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

		// TODO load internal data.
		// Everything handed to output ports is loaded automatically (data
		// returned by the execute method, models loaded in loadModelContent,
		// and user settings set through loadSettingsFrom - is all taken care
		// of). Load here only the other internals that need to be restored
		// (e.g. data used by the views).

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveInternals(final File internDir, final ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

		// TODO save internal models.
		// Everything written to output ports is saved automatically (data
		// returned by the execute method, models saved in the saveModelContent,
		// and user settings saved through saveSettingsTo - is all taken care
		// of). Save here only the other internals that need to be preserved
		// (e.g. data used by the views).

	}

}
