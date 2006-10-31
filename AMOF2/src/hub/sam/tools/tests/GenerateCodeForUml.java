package hub.sam.tools.tests;

import java.io.File;

import hub.sam.mof.ant.GenerateCode;

public class GenerateCodeForUml extends GenerateCode {

	public static void main(String[] args) {
		GenerateCodeForUml my = new GenerateCodeForUml();
		my.setSrc(new File("resources/models/new-models/uml/L3.merged.cmof.xml"));
		my.setDestdir(new File("resources/uml-src"));
		my.execute();
	}

}
