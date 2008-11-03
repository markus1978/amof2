package hub.sam.mof.emf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

import cmof.Type;

public class Simplificator {
	
	public static void simplify(cmof.Package packageToSimplify) {
		List<cmof.Package> packagesToRemove = new ArrayList<cmof.Package>();
		for(cmof.Package aPackage: packageToSimplify.getNestedPackage()) {
			simplify(aPackage);
			List<Type> typesToMove = new ArrayList<Type>();
			for(Type aType: aPackage.getOwnedType()) {
				aType.setName(aPackage.getName() + aType.getName()); 
				typesToMove.add(aType);
			}
			for(Type aType: typesToMove) {
				aType.setPackage(packageToSimplify);
			}
			packagesToRemove.add(aPackage);
		}
		for(cmof.Package aPackage: packagesToRemove) {
			aPackage.delete();
		}
	}
	
	public static void simplify(EPackage packageToSimplify) {
		List<EPackage> packagesToRemove = new ArrayList<EPackage>();
		for(EPackage aPackage: packageToSimplify.getESubpackages()) {
			simplify(aPackage);
			List<EClassifier> typesToMove = new ArrayList<EClassifier>();
			for(EClassifier aType: aPackage.getEClassifiers()) {
				aType.setName(aPackage.getName() + aType.getName()); 
				typesToMove.add(aType);
			}
			for(EClassifier aType: typesToMove) {
				packageToSimplify.getEClassifiers().add(aType);
			}
			packagesToRemove.add(aPackage);
		}
		for(EPackage aPackage: packagesToRemove) {
			((EPackage)aPackage.eContainer()).getESubpackages().remove(aPackage);
		}
	}
}
