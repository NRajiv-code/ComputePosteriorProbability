import java.util.ArrayList;

public class bnet {
    static double Burglary = 0.001;
    static double Earthquake = 0.002;
    static ArrayList<String> a1 = new ArrayList<String>();
    static ArrayList<String> a2 = new ArrayList<String>();
    static double Alarm[] = { 0.95, 0.94, 0.29, 0.001 };
    static double John[] = { 0.90, 0.05 };
    static double Mary[] = { 0.70, 0.01 };

    public static void main(String[] args) {

        int BurCounter = 0, EarthCounter = 0, AlarmCounter = 0, JohnCounter = 0, MaryCounter = 0;
        if (args.length < 1 || args.length > 6) {
            System.exit(0);
        }
        int idx = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("given")) {
                idx = 0;
                continue;
            }
            if (idx == -1) {
                a1.add(args[i]);
            } else {
                a2.add(args[i]);
            }

        }
        if (a1.size() < 1 || a1.size() > 6) {
            System.exit(0);
        }
        if (idx == 0) {
            if (a2.size() < 1 || a2.size() > 4) {
                System.exit(0);
            }
        }

        System.out.println(a1 + "given" + a2);
        a1.addAll(a2);

        for (int i = 0; i < a1.size(); i++) {

            if (!a1.contains("Bt") && !a1.contains("Bf")) {
                a1.add("Bt");
                a1.add("Bf");
                BurCounter = 1;
            }
            if (!a1.contains("Et") && !a1.contains("Ef")) {
                a1.add("Et");
                a1.add("Ef");
                EarthCounter = 1;
            }
            if (!a1.contains("At") && !a1.contains("Af")) {
                a1.add("At");
                a1.add("Af");
                AlarmCounter = 1;
            }
            if (!(a1.contains("Jt")) && !a1.contains("Jf")) {
                a1.add("Jt");
                a1.add("Jf");
                JohnCounter = 1;
            }
            if (!a1.contains("Mt") && !a1.contains("Mf")) {
                a1.add("Mt");
                a1.add("Mf");
                MaryCounter = 1;
            }
        }

        double num = Calculate_Probability(BurCounter, EarthCounter, AlarmCounter, JohnCounter, MaryCounter, a1);
        if (a2.size() == 0) {
            System.out.println("Calculated Probability: " + num);
        }
        BurCounter = EarthCounter = AlarmCounter = JohnCounter = MaryCounter = 0;
        for (int john = 0; john < a2.size(); john++) {

            if (!a2.contains("Bt") && !a2.contains("Bf")) {
                a2.add("Bt");
                a2.add("Bf");
                BurCounter = 1;
            }
            if (!a2.contains("Et") && !a2.contains("Ef")) {
                a2.add("Et");
                a2.add("Ef");
                EarthCounter = 1;
            }
            if (!a2.contains("At") && !a2.contains("Af")) {
                a2.add("At");
                a2.add("Af");
                AlarmCounter = 1;
            }
            if (!a2.contains("Jt") && !a2.contains("Jf")) {
                a2.add("Jt");
                a2.add("Jf");
                JohnCounter = 1;
            }
            if (!a2.contains("Mt") && !a2.contains("Mf")) {
                a2.add("Mt");
                a2.add("Mf");
                MaryCounter = 1;
            }
        }
        double den = Calculate_Probability(BurCounter, EarthCounter, AlarmCounter, JohnCounter, MaryCounter, a2);
        if (a2.size() > 0) {
            System.out.println("Calculated Probability: " + num / den);
        }

    }

    public static double Calculate_Probability(int burc, int earthc, int alarmc, int johnc, int maryc,
            ArrayList<String> aproc) {
        double probability = 0.0;
        Boolean burglar_bool = false, earth_bool = false, alarm_bool = false, john_bool = false, mary_bool = false;
        if (burc == 0) {
            if (aproc.contains("Bt")) {
                burglar_bool = true;
            } else
                burglar_bool = false;
        }
        if (earthc == 0) {
            if (aproc.contains("Et")) {
                earth_bool = true;
            } else
                earth_bool = false;
        }
        if (alarmc == 0) {
            if (aproc.contains("At")) {
                alarm_bool = true;
            } else
                alarm_bool = false;
        }
        if (johnc == 0) {
            if (aproc.contains("Jt")) {
                john_bool = true;
            } else
                john_bool = false;
        }
        if (maryc == 0) {
            if (aproc.contains("Mt")) {
                mary_bool = true;
            } else
                mary_bool = false;
        }
        for (int f1 = 0; f1 <= burc; f1++) {
            for (int f2 = 0; f2 <= earthc; f2++) {
                for (int f3 = 0; f3 <= alarmc; f3++) {
                    for (int f4 = 0; f4 <= johnc; f4++) {
                        for (int f5 = 0; f5 <= maryc; f5++) {
                            probability += cprobability(burglar_bool, earth_bool, alarm_bool, john_bool,
                                    mary_bool);
                            if (maryc == 1 && mary_bool == false)
                                mary_bool = true;
                            else if (maryc == 1 && mary_bool == true)
                                mary_bool = false;
                        }
                        if (johnc == 1 && john_bool == false)
                            john_bool = true;
                        else if (johnc == 1 && john_bool == true)
                            john_bool = false;
                    }
                    if (alarmc == 1 && alarm_bool == false)
                        alarm_bool = true;
                    else if (alarmc == 1 && alarm_bool == true)
                        alarm_bool = false;
                }
                if (earthc == 1 && earth_bool == false)
                    earth_bool = true;
                else if (earthc == 1 && earth_bool == true)
                    earth_bool = false;
            }
            if (burc == 1 && burglar_bool == false)
                burglar_bool = true;
            else if (burc == 1 && burglar_bool == true)
                burglar_bool = false;
        }
        return probability;
    }

    public static double cprobability(boolean bur, boolean earth, boolean alarm, boolean john, boolean mary) {
        double BurgVal = 0.0;
        if (bur) {
            BurgVal = Burglary;
        } else {
            BurgVal = 1 - Burglary;
        }
        double EarthVal;
        if (earth) {
            EarthVal = Earthquake;
        } else {
            EarthVal = 1 - Earthquake;
        }
        double AlarmVal = 0.0;
        if (alarm) {
            if (bur == true && earth == true)
                AlarmVal = Alarm[0];
            else if (bur == true && earth == false)
                AlarmVal = Alarm[1];
            else if (bur == false && earth == true)
                AlarmVal = Alarm[2];
            else if (bur == false && earth == false)
                AlarmVal = Alarm[3];
        } else {
            if (bur == true && earth == true)
                AlarmVal = 1 - Alarm[0];
            else if (bur == true && earth == false)
                AlarmVal = 1 - Alarm[1];
            else if (bur == false && earth == true)
                AlarmVal = 1 - Alarm[2];
            else if (bur == false && earth == false)
                AlarmVal = 1 - Alarm[3];
        }

        double JohnVal = 0.0;
        if (john) {
            if (alarm == true)
                JohnVal = John[0];
            else if (alarm == false)
                JohnVal = John[1];
        }

        else {
            if (alarm == true)
                JohnVal = 1 - John[0];
            else if (alarm == false)
                JohnVal = 1 - John[1];
        }

        double MaryVal = 0.0;
        if (mary) {
            if (alarm == true)
                MaryVal = Mary[0];
            else if (alarm == false)
                MaryVal = Mary[1];
        }

        else {
            if (alarm == true)
                MaryVal = 1 - Mary[0];
            else if (alarm == false)
                MaryVal = 1 - Mary[1];
        }
        return BurgVal * EarthVal * AlarmVal * JohnVal * MaryVal;
    }

}