package NDT.ReportObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reports {

    @Getter @Setter
    private String reportID;
    private ReportResponsible vik = new ReportResponsible();
    private ReportResponsible rk = new ReportResponsible();
    private ReportResponsible uzk = new ReportResponsible();
    private ReportResponsible pvk = new ReportResponsible();

    private Map<String,String> specMap = new HashMap<>();

    public ReportResponsible getResponsible(String ntdType) {
        ReportResponsible result = null;
        if(ntdType.equals("вик")) result = vik;
        if(ntdType.equals("рк")) result = rk;
        if(ntdType.equals("ук")) result = uzk;
        if(ntdType.equals("пвк")) result = pvk;
        return result;
    }

    public void setResponsible(ReportResponsible report,String ntdType) {
        if(ntdType.equals("вик")) this.vik = report;
        if(ntdType.equals("рк")) this.rk = report;
        if(ntdType.equals("ук")) this.uzk = report;
        if(ntdType.equals("пвк")) this.pvk = report;
    }

    public List<ReportResponsible> getAllReports(){
        List<ReportResponsible> reportList = new ArrayList<>();
        reportList.add(vik);
        reportList.add(pvk);
        reportList.add(rk);
        reportList.add(uzk);
        return reportList;
    }

    public Map<String, String> getSpecMap() {
        this.getAllReports().forEach(e->{
            this.specMap.putIfAbsent(e.getTestedBy(),e.getTestedBy());
            this.specMap.putIfAbsent(e.getConclusionAccept(),e.getConclusionAccept());
            this.specMap.putIfAbsent(e.getConclusionIssued(),e.getConclusionIssued());
            this.specMap.putIfAbsent(e.getHeadOfLaboratory(),e.getHeadOfLaboratory());
        });

        return specMap;
    }
}
