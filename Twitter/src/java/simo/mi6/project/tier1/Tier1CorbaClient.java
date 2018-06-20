package simo.mi6.project.tier1;

import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import simo.mi6.project.tier2.Tier2;
import simo.mi6.project.tier2.Tier2Helper;

public class Tier1CorbaClient {

    public static void main(String[] args) throws Exception {

        // Paramétrage pour la création de la couche ORB :
        // localisation de l'annuaire d'objet (service nommage)
        Properties props = new Properties();
        props.put("org.omg.CORBA.ORBInitialPort", "2000");
        props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");

        // Création de la couche ORB
        // pour communiquer via un bus CORBA
        ORB orb = ORB.init((String[]) null, props);

        // Recherche d'une référence sur un service de nommage
        org.omg.CORBA.Object serviceNommageRef;
        serviceNommageRef = orb.resolve_initial_references("NameService");

        // Instance du service de nommage à partir de sa référence
        // ("cast" façon CORBA)
        NamingContextExt serviceNommage = NamingContextExtHelper.narrow(serviceNommageRef);

        // Recherche d'une référence sur le service Tier2
        org.omg.CORBA.Object serviceTier2Ref;
        serviceTier2Ref = serviceNommage.resolve_str("Tier2");

        // Instance du service Tier2 à partir de sa référence (cast)
        Tier2 serviceCorbaTier2 = Tier2Helper.narrow(serviceTier2Ref);

        for (String user : serviceCorbaTier2.getAllUsers()) {
            System.out.println(user);

        }
    }

}
