package creditCardFiles;


public enum USState {

	AL, AK, AS, AZ, AR, CA, CO, CT, DE, DC, FM, FL, GA, GU, HI, ID, 
	IL, IN, IA, KS, KY, LA, ME, MH, MD, 
	MA, MI, MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, MP, 
	OH, OK, OR, PW, PA, PR, RI, SC, SD, TN, TX, UT, VT, VI, VA, WA, WV, WI, WY;
 
    static public boolean isMember(String aName) {
        USState[] states = USState.values();
        for (USState state : states)
            if (state.toString().equals(aName))
                return true;
        return false;
    }
}

