package simo.mi6.project.tier2;

import simo.mi6.project.tier2.Corba.Tier2Impl;
import simo.mi6.project.tier2.Corba.Tier2;
import simo.mi6.project.tier2.Corba.Tier2Helper;
import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NameComponent;

public class ServiceCorbaTier2 {

    public static void main(String[] args) throws Exception {
        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "2000");
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");

        // Création de la couche ORB
        // pour communiquer via un bus CORBA
        ORB orb = ORB.init((String[]) null, props);

        // Recherche d'une reference au service "RootPOA"
        // (Portable Object Adaptator)
        org.omg.CORBA.Object poaRef = orb.resolve_initial_references("RootPOA");

        // Instance du service RootPOA ("cast" sauce CORBA)
        POA rootPOA = POAHelper.narrow(poaRef);

        // Activation du service RootPOA
        rootPOA.the_POAManager().activate();

        // Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");

        // Instance du service de nommage à partir de sa référence
        // ("cast" façon CORBA)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);

        // Création du service et de son enveloppe (par héritage)
        Tier2Impl tier2Impl = new Tier2Impl();

        // Creation de la référence CORBA du service auprès du POA
        org.omg.CORBA.Object tier2Ref;
        tier2Ref = rootPOA.servant_to_reference(tier2Impl);

        // Création de l'objet CORBA du service
        Tier2 tier2 = Tier2Helper.narrow(tier2Ref);

        // Enregistrement du service (nom, objet CORBA)
        NameComponent[] path = serviceNommage.to_name("Tier2");
        // serviceNommage.unbind(path);
        // serviceNommage.bind(path, tier2);
        serviceNommage.rebind(path, tier2);

        // Démarrage de la couche ORB
        orb.run();

        System.out.println("**Serveur Corba démarré**");
    }

}
