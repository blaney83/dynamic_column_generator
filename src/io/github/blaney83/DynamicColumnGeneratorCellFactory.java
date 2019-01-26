package io.github.blaney83;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataRow;
import org.knime.core.data.container.SingleCellFactory;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.StringCell;

public class DynamicColumnGeneratorCellFactory extends SingleCellFactory {
	private final boolean m_columnType;
	
	public DynamicColumnGeneratorCellFactory(final DataColumnSpec newColSpec, final boolean columnType) {
		super(newColSpec);
		m_columnType = columnType;
	}
	
	@Override
	public DataCell getCell(DataRow row) {
		if(m_columnType) {
			char[] charArr = new char[(int)Math.floor(Math.random()*10)+1];
			StringBuilder stringBuilder = new StringBuilder();
			for(char letter : charArr) {
				letter = (char)(Math.floor(Math.random()*25)+66);
				stringBuilder.append(letter);
			}
			return new StringCell(stringBuilder.toString());
		}else {
			Double coinFlip = Math.random();
			if(coinFlip < .5) {
				coinFlip = -1D;
			}else {
				coinFlip = 1D;
			}
			return new DoubleCell(Math.random()*((Double)Double.MAX_VALUE)*(coinFlip));
		}
		
	}

}
