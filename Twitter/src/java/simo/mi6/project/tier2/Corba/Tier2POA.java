package simo.mi6.project.tier2.Corba;


/**
* tier2/Tier2POA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from tier2.idl
* jeudi 21 juin 2018 14 h 34 CEST
*/

public abstract class Tier2POA extends org.omg.PortableServer.Servant
 implements simo.mi6.project.tier2.Corba.Tier2Operations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createUser", new java.lang.Integer (0));
    _methods.put ("deleteUser", new java.lang.Integer (1));
    _methods.put ("userConnexion", new java.lang.Integer (2));
    _methods.put ("createNewTweet", new java.lang.Integer (3));
    _methods.put ("getTweetsForUser", new java.lang.Integer (4));
    _methods.put ("startFollowing", new java.lang.Integer (5));
    _methods.put ("stopFollowing", new java.lang.Integer (6));
    _methods.put ("searchForUser", new java.lang.Integer (7));
    _methods.put ("searchInTweets", new java.lang.Integer (8));
    _methods.put ("getAllUsers", new java.lang.Integer (9));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // tier2/Tier2/createUser
       {
         String username = in.read_string ();
         String password = in.read_string ();
         boolean $result = false;
         $result = this.createUser (username, password);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // tier2/Tier2/deleteUser
       {
         String username = in.read_string ();
         boolean $result = false;
         $result = this.deleteUser (username);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // tier2/Tier2/userConnexion
       {
         String username = in.read_string ();
         String password = in.read_string ();
         boolean $result = false;
         $result = this.userConnexion (username, password);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 3:  // tier2/Tier2/createNewTweet
       {
         String username = in.read_string ();
         String password = in.read_string ();
         String tweet = in.read_string ();
         boolean $result = false;
         $result = this.createNewTweet (username, password, tweet);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // tier2/Tier2/getTweetsForUser
       {
         String username = in.read_string ();
         String $result[] = null;
         $result = this.getTweetsForUser (username);
         out = $rh.createReply();
         simo.mi6.project.tier2.Corba.StringsHelper.write (out, $result);
         break;
       }

       case 5:  // tier2/Tier2/startFollowing
       {
         String followerUsername = in.read_string ();
         String followedUsername = in.read_string ();
         boolean $result = false;
         $result = this.startFollowing (followerUsername, followedUsername);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 6:  // tier2/Tier2/stopFollowing
       {
         String followerUsername = in.read_string ();
         String followedUsername = in.read_string ();
         boolean $result = false;
         $result = this.stopFollowing (followerUsername, followedUsername);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 7:  // tier2/Tier2/searchForUser
       {
         String searchUsername = in.read_string ();
         String $result[] = null;
         $result = this.searchForUser (searchUsername);
         out = $rh.createReply();
         simo.mi6.project.tier2.Corba.StringsHelper.write (out, $result);
         break;
       }

       case 8:  // tier2/Tier2/searchInTweets
       {
         String searchString = in.read_string ();
         String $result[] = null;
         $result = this.searchInTweets (searchString);
         out = $rh.createReply();
         simo.mi6.project.tier2.Corba.StringsHelper.write (out, $result);
         break;
       }

       case 9:  // tier2/Tier2/getAllUsers
       {
         String $result[] = null;
         $result = this.getAllUsers ();
         out = $rh.createReply();
         simo.mi6.project.tier2.Corba.StringsHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:tier2/Tier2:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Tier2 _this() 
  {
    return Tier2Helper.narrow(
    super._this_object());
  }

  public Tier2 _this(org.omg.CORBA.ORB orb) 
  {
    return Tier2Helper.narrow(
    super._this_object(orb));
  }


} // class Tier2POA
