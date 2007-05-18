
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class ContextDeclarationASImpl

  extends
    
      org.oslo.ocl20.syntax.ast.SyntaxElementImpl
    
  
  implements ContextDeclarationAS,
             contextsVisitable
{
  //--- Constructors ---
  public ContextDeclarationASImpl() {

    _id=_nextId++;

  }







    
 
 

  //bi1toN
  protected PackageDeclarationAS packageDeclarationAS = null;
  public PackageDeclarationAS getPackageDeclarationAS() {
   return this.packageDeclarationAS;
  }
  public void setPackageDeclarationAS(PackageDeclarationAS packageDeclarationAS) {
	//If aggregate part then this pool contained by one parent diagram only
	if (this.packageDeclarationAS != null)
		this.packageDeclarationAS.removeContextDecls(this);
    this.packageDeclarationAS = packageDeclarationAS;
    if (packageDeclarationAS != null)
      if (!packageDeclarationAS.getContextDecls().contains(this)) {
        packageDeclarationAS.addContextDecls(this);
     }
  }



    
 
 

  //biNto1
  public static ConstraintAS constraints_elementType;
  protected java.util.List constraints = new java.util.Vector();
  public java.util.List getConstraints() {
  	final ContextDeclarationAS newContextDeclarationAS = this;
  	return new java.util.Vector(constraints) {
		public boolean add(Object o) {
			ConstraintAS p = (ConstraintAS)o;
			if (newContextDeclarationAS == null)
				return super.add(p);
			else
				return newContextDeclarationAS.addConstraints(p);
  		}
		public boolean remove(Object o) {
			ConstraintAS p = (ConstraintAS)o;
			if (newContextDeclarationAS == null)
				return super.remove(p);
			else
				return newContextDeclarationAS.removeConstraints(p);
		}
     };
  }
  public void setConstraints(java.util.List constraints) {
    this.constraints = constraints;
    java.util.Iterator i = constraints.iterator();
    while(i.hasNext()) {
      ConstraintAS constraintsObj = (ConstraintAS)i.next();
      if (constraintsObj.getContextDeclarationAS() != this) {
      	constraintsObj.setContextDeclarationAS(this);
      }
    }
  }
  public boolean addConstraints(ConstraintAS p) {
	boolean b = this.constraints.add(p);
	if (b && !this.equals(p.getContextDeclarationAS()))
		p.setContextDeclarationAS(this);
	return b;
  }
 
  public boolean removeConstraints(ConstraintAS p) {
	boolean b = this.constraints.remove(p);
	if (b && this.equals(p.getContextDeclarationAS()))
		p.setContextDeclarationAS(null);
	return b;
  }






  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof contextsVisitor)
      return ((contextsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("ContextDeclarationAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ContextDeclarationAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "ContextDeclarationAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof ContextDeclarationAS) {
      try {
        ContextDeclarationAS obj = (ContextDeclarationAS)o;
        return (this == o);
      } catch (NullPointerException e) {
        return this == o;
      }
    }
    return false;
  }

  public int hashCode() {
      return 0;
  }

  public java.lang.Object clone() {
    ContextDeclarationAS obj = new ContextDeclarationASImpl();

    return obj;
  }
}



