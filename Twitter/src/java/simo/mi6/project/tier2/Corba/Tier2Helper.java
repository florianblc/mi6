package simo.mi6.project.tier2.Corba;


/**
* tier2/Tier2Helper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from tier2.idl
* jeudi 21 juin 2018 14 h 34 CEST
*/

abstract public class Tier2Helper
{
  private static String  _id = "IDL:tier2/Tier2:1.0";

  public static void insert (org.omg.CORBA.Any a, simo.mi6.project.tier2.Corba.Tier2 that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static simo.mi6.project.tier2.Corba.Tier2 extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (simo.mi6.project.tier2.Corba.Tier2Helper.id (), "Tier2");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static simo.mi6.project.tier2.Corba.Tier2 read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_Tier2Stub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, simo.mi6.project.tier2.Corba.Tier2 value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static simo.mi6.project.tier2.Corba.Tier2 narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof simo.mi6.project.tier2.Corba.Tier2)
      return (simo.mi6.project.tier2.Corba.Tier2)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      simo.mi6.project.tier2.Corba._Tier2Stub stub = new simo.mi6.project.tier2.Corba._Tier2Stub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static simo.mi6.project.tier2.Corba.Tier2 unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof simo.mi6.project.tier2.Corba.Tier2)
      return (simo.mi6.project.tier2.Corba.Tier2)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      simo.mi6.project.tier2.Corba._Tier2Stub stub = new simo.mi6.project.tier2.Corba._Tier2Stub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
