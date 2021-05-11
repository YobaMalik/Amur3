package Back.Pasport.Part5.Element;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class Elements{
 @Getter @Setter private String lineNumber;
 @Getter @Setter private String elementName;
 @Getter @Setter private String dimension;
 @Getter @Setter private String grade;
 @Getter @Setter private String gost;
 @Getter @Setter private Integer nn;
 @Getter @Setter private String desPress;
 @Getter @Setter private String AElementGost;
 @Getter @Setter private String AElementGrade;
 @Getter @Setter private String fGost;
 @Getter @Setter private String idCode;

 @Override
 public int hashCode() {
  return super.hashCode();
 }

 @Override
 public boolean equals(Object obj) {
  if(obj == null) return false;
  if(!(obj instanceof Elements)) return false;
  if(obj == this) return false;
  Elements elm = (Elements) obj;
  if(!this.elementName.equals(elm.elementName) &&
          !this.desPress.equals(elm.desPress) &&
          !this.dimension.equals(elm.dimension)&&
          !this.gost.equals(elm.gost)&&
          !this.grade.equals(elm.grade)
  ) return false;
  return true;
 }
}
