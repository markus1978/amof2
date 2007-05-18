
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class PackageDeclarationASImpl

  extends
    
      org.oslo.ocl20.syntax.ast.SyntaxElementImpl
    
  
  implements PackageDeclarationAS,
             contextsVisitable
{
  //--- Constructors ---
  public PackageDeclarationASImpl() {

  }




  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }




    
 
 

  //biNto1
  public static ContextDeclarationAS contextDecls_elementType;
  protected java.util.List contextDecls = new java.util.Vector();
  public java.util.List getContextDecls() {
  	final PackageDeclarationAS newPackageDeclarationAS = this;
  	return new java.util.Vector(contextDecls) {
		public boolean add(Object o) {
			ContextDeclarationAS p = (ContextDeclarationAS)o;
			if (newPackageDeclarationAS == null)
				return super.add(p);
			else
				return newPackageDeclarationAS.addContextDecls(p);
  		}
		public boolean remove(Object o) {
			ContextDeclarationAS p = (ContextDeclarationAS)o;
			if (newPackageDeclarationAS == null)
				return super.remove(p);
			else
				return newPackageDeclarationAS.removeContextDecls(p);
		}
     };
  }
  public void setContextDecls(java.util.List contextDecls) {
    this.contextDecls = contextDecls;
    java.util.Iterator i = contextDecls.iterator();
    while(i.hasNext()) {
      ContextDeclarationAS contextDeclsObj = (ContextDeclarationAS)i.next();
      if (contextDeclsObj.getPackageDeclarationAS() != this) {
      	contextDeclsObj.setPackageDeclarationAS(this);
      }
    }
  }
  public boolean addContextDecls(ContextDeclarationAS p) {
	boolean b = this.contextDecls.add(p);
	if (b && !this.equals(p.getPackageDeclarationAS()))
		p.setPackageDeclarationAS(this);
	return b;
  }
 
  public boolean removeContextDecls(ContextDeclarationAS p) {
	boolean b = this.contextDecls.remove(p);
	if (b && this.equals(p.getPackageDeclarationAS()))
		p.setPackageDeclarationAS(null);
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
  	factory.destroy("PackageDeclarationAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PackageDeclarationAS",this);
  }


  //--- java.lang.Object ---


  public java.lang.String toString() {
      return "PackageDeclarationAS {"+

"pathName = "+this.getPathName()+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof PackageDeclarationAS) {
      try {
        PackageDeclarationAS obj = (PackageDeclarationAS)o;
        return (
obj.getPathName().equals( this.getPathName() )
);
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
    PackageDeclarationAS obj = new PackageDeclarationASImpl();

  obj.setPathName(_pathName);

    return obj;
  }
}



