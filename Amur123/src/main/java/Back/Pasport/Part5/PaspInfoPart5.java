package Back.Pasport.Part5;


import Back.Pasport.Part5.Element.Elements;
import org.apache.poi.ss.usermodel.*;

import java.text.ParseException;
import java.util.*;

public class PaspInfoPart5 extends AbstractPart5  {
	private int nameElem;
	private int eThikness;
	private int eSteel;
	private int eGost;
	private int linePos;
	private int rowIndex;

	private String[] paspPart;

	public PaspInfoPart5(Workbook wb) {
		super(wb);
	}


	private boolean pasreCellValue(Cell cell) {
		try {
			cell.getStringCellValue();
			return true;
		} catch(IllegalStateException e) {
			return false;
		}

	}
	@Override
	protected void getInfoPart5(String paspPart) {
		Sheet iSheet=this.wb.getSheet(paspPart);
		Iterator<Row> rIter=iSheet.rowIterator();
		while(rIter.hasNext()) {
			Iterator<Cell> cIter=rIter.next().cellIterator();
			while(cIter.hasNext()) {
				Cell cell=cIter.next();

				//	System.out.println("row "+cell.getRowIndex()+ " column "+cell.getColumnIndex());
				if(cell!=null&&cell.toString().toLowerCase().contains("наименов")&&
						cell.getStringCellValue().toLowerCase().contains("name")) {
					this.nameElem=cell.getColumnIndex();
				}
				if(cell!=null&&
						cell.toString().toLowerCase().replace(" ","").contains("п/п")) {
					this.linePos=cell.getRowIndex()+1;
					this.rowIndex=cell.getColumnIndex();

				}

				if(cell!=null&& cell.toString().toLowerCase().contains("размер") &&
						cell.toString().toLowerCase().contains("dimens")) {
					this.eThikness=cell.getColumnIndex();
				}

				if(cell!=null&&cell.toString().toLowerCase().contains("марк")
						&&cell.getStringCellValue().toLowerCase().contains("стал")) {
					this.eSteel=cell.getColumnIndex();
				}

				if(cell!=null&&cell.toString().toLowerCase().contains("гост")&&
						cell.getStringCellValue().toLowerCase().contains("ту")) {
					this.eGost=cell.getColumnIndex();
				}

			}
		}
	}

	@Override
	public List<Elements> fillTable(String... paspPart) throws ParseException {
		List<Elements> resultList =new ArrayList<>();
		this.paspPart=paspPart;

		for(int z=0;z<this.paspPart.length;z++) {
			this.getInfoPart5(this.paspPart[z]);
			Sheet sht = this.wb.getSheet(this.paspPart[z]);
			String tempLine = null;

			for (int i = this.linePos + 1; i < sht.getLastRowNum() + 1; i++) {
				Elements element51 = new Elements();
				if (sht.getRow(i) != null &&
						sht.getRow(i).getCell(this.nameElem) != null &&
						sht.getRow(i).getCell(this.nameElem).getCellType() != CellType.BLANK) {

					String nameElem = sht.getRow(i).getCell(this.nameElem).toString();
					String eThikness = sht.getRow(i).getCell(this.eThikness).toString();
					String eSteel = sht.getRow(i).getCell(this.eSteel).toString();
					String eGost = sht.getRow(i).getCell(this.eGost).toString();
					String nn = sht.getRow(i).getCell(this.rowIndex).toString();


					element51.setElementName(nameElem);
					element51.setDimension(eThikness.replace(".0",""));
					element51.setGrade(eSteel);
					element51.setGost(eGost);
					element51.setNn(this.convToInt(nn));

					resultList.add(element51);
				}
				else {
					if(sht.getRow(i) != null && sht.getRow(i).getCell(this.rowIndex) != null &&
					!sht.getRow(i).getCell(this.rowIndex).toString().isEmpty()){
						tempLine = sht.getRow(i).getCell(this.rowIndex).toString();
					}

				}
				element51.setLineNumber(tempLine);
			}

		}
		return resultList;
	}
}
