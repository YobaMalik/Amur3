package NDT.ReportObjects;

import lombok.Getter;
import lombok.Setter;


public class ReportResponsible {
    @Getter @Setter
    private String line;
    @Getter @Setter
    private String zone;
    @Getter @Setter
    private String ntdType;
    @Getter @Setter
    private String testedBy;
    @Getter @Setter
    private String headOfLaboratory;
    @Getter @Setter
    private String conclusionAccept;
    @Getter @Setter
    private String conclusionIssued;
    @Getter @Setter
    private String reportNumber;
}
