package FrontOBRE;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame {
    private String inputFilePath;

    public void setInputFilePath() throws IOException {
      JFileChooser file = new JFileChooser(){
          @Override
          protected JDialog createDialog(Component parent)
                  throws HeadlessException {
              JDialog dialog = super.createDialog(parent);
              dialog.setLocationByPlatform(true);
              dialog.setAlwaysOnTop(true);
              return dialog;
          }
      };

        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        file.showOpenDialog(null);
        inputFilePath = file.getSelectedFile().getPath();

 /*
        JComboBox pasportBox = new JComboBox(this.pasportsList().toArray(new String[0]));
        Container container = this.getContentPane();

        JPanel panel = new JPanel();
        panel.add(pasportBox);
        panel.setPreferredSize(new Dimension(240, 130));

        container.add(panel);
     //   this.setPreferredSize(new Dimension(240, 130));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
  */
    }

    public String getInputFilePath() {
        return inputFilePath;
    }


    private List<String> pasportsList() throws IOException {
        List<String> reslutList = new ArrayList<>();

        try(InputStream in = MyFrame.class.getResourceAsStream("/PasportList.xlsx");
            Workbook wb = new XSSFWorkbook(in)){
            Sheet sheet = wb.getSheet("List");
            for(int i = 0; i < sheet.getLastRowNum(); i++){
                Cell cell = sheet.getRow(i).getCell(0);
                if(cell!=null) reslutList.add(cell.toString());
            }
        }
        return reslutList;
    }
}
