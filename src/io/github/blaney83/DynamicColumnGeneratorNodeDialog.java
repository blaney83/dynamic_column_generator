package io.github.blaney83;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * <code>NodeDialog</code> for the "DynamicColumnGenerator" Node.
 * A dynamic column generator for appending columns to tables to assist with workflow testing, visualization techniques and general validation.
 * 
 * @author Ben Laney
 */
public class DynamicColumnGeneratorNodeDialog extends DefaultNodeSettingsPane {

    protected DynamicColumnGeneratorNodeDialog() {
        super();
        
        addDialogComponent(new DialogComponentString(
                new SettingsModelString(
                    DynamicColumnGeneratorNodeModel.CFGKEY_NEW_COLUMN_NAME, "Generated Column"),
                	"New column name: ", true, 25
                    ));
                    
        addDialogComponent(new DialogComponentBoolean(
        		new SettingsModelBoolean(
        				DynamicColumnGeneratorNodeModel.CFGKEY_STRING_COLUMN, false)
        		, "Check for string column, blank for double column."));
    }
}

