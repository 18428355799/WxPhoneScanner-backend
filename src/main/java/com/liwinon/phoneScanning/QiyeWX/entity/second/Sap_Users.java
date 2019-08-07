package com.liwinon.phoneScanning.QiyeWX.entity.second;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  SAP 系统
 *  HRM 数据库
 */
@Entity
@Table(name = "Sap_Users")
public class Sap_Users {
    @Id
    private String SAP_ID;
    private String ORDER_BY;
    private String SATUS;
    private String MODITYPE;
    private String PLANT;
    private String PLANTXT;
    private String AREA;
    private String AREATXT;
    private String ZGROUP;
    private String GROUPTXT;
    private String SUBGROUP;
    private String SUBGROUPTXT;
    private String PAYRANGE;
    private String PAYRANGETXT;
    private String ORGID;
    private String ORGIDERP;
    private String ORGTXT;
    private String POSITIONID;
    private String POSITIONIDTXT;
    private String PERSONID;
    private String NAME;
    private String ENAME;
    private String GENDER;
    private String SPECIALJOB;
    private String STOCKSTIMULATE;
    private String FRESHYEAR;
    private String IDCARD;
    private String ADDRESS;
    private String EDUCATION;
    private String MAJOR;
    private String GRADUATEDATE;
    private String ENTRYDATE;
    private String QUALIFYDATE;
    private String EMAIL;
    private String TELEPHONE;
    private String WXID;
    private String HLEADER;
    private String RANK;
    private String POSTGG;
    private String PUBLIC2;

    public String getSAP_ID() {
        return SAP_ID;
    }

    public void setSAP_ID(String SAP_ID) {
        this.SAP_ID = SAP_ID;
    }

    public String getORDER_BY() {
        return ORDER_BY;
    }

    public void setORDER_BY(String ORDER_BY) {
        this.ORDER_BY = ORDER_BY;
    }

    public String getSATUS() {
        return SATUS;
    }

    public void setSATUS(String SATUS) {
        this.SATUS = SATUS;
    }

    public String getMODITYPE() {
        return MODITYPE;
    }

    public void setMODITYPE(String MODITYPE) {
        this.MODITYPE = MODITYPE;
    }

    public String getPLANT() {
        return PLANT;
    }

    public void setPLANT(String PLANT) {
        this.PLANT = PLANT;
    }

    public String getPLANTXT() {
        return PLANTXT;
    }

    public void setPLANTXT(String PLANTXT) {
        this.PLANTXT = PLANTXT;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public String getAREATXT() {
        return AREATXT;
    }

    public void setAREATXT(String AREATXT) {
        this.AREATXT = AREATXT;
    }

    public String getZGROUP() {
        return ZGROUP;
    }

    public void setZGROUP(String ZGROUP) {
        this.ZGROUP = ZGROUP;
    }

    public String getGROUPTXT() {
        return GROUPTXT;
    }

    public void setGROUPTXT(String GROUPTXT) {
        this.GROUPTXT = GROUPTXT;
    }

    public String getSUBGROUP() {
        return SUBGROUP;
    }

    public void setSUBGROUP(String SUBGROUP) {
        this.SUBGROUP = SUBGROUP;
    }

    public String getSUBGROUPTXT() {
        return SUBGROUPTXT;
    }

    public void setSUBGROUPTXT(String SUBGROUPTXT) {
        this.SUBGROUPTXT = SUBGROUPTXT;
    }

    public String getPAYRANGE() {
        return PAYRANGE;
    }

    public void setPAYRANGE(String PAYRANGE) {
        this.PAYRANGE = PAYRANGE;
    }

    public String getPAYRANGETXT() {
        return PAYRANGETXT;
    }

    public void setPAYRANGETXT(String PAYRANGETXT) {
        this.PAYRANGETXT = PAYRANGETXT;
    }

    public String getORGID() {
        return ORGID;
    }

    public void setORGID(String ORGID) {
        this.ORGID = ORGID;
    }

    public String getORGIDERP() {
        return ORGIDERP;
    }

    public void setORGIDERP(String ORGIDERP) {
        this.ORGIDERP = ORGIDERP;
    }

    public String getORGTXT() {
        return ORGTXT;
    }

    public void setORGTXT(String ORGTXT) {
        this.ORGTXT = ORGTXT;
    }

    public String getPOSITIONID() {
        return POSITIONID;
    }

    public void setPOSITIONID(String POSITIONID) {
        this.POSITIONID = POSITIONID;
    }

    public String getPOSITIONIDTXT() {
        return POSITIONIDTXT;
    }

    public void setPOSITIONIDTXT(String POSITIONIDTXT) {
        this.POSITIONIDTXT = POSITIONIDTXT;
    }

    public String getPERSONID() {
        return PERSONID;
    }

    public void setPERSONID(String PERSONID) {
        this.PERSONID = PERSONID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getENAME() {
        return ENAME;
    }

    public void setENAME(String ENAME) {
        this.ENAME = ENAME;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getSPECIALJOB() {
        return SPECIALJOB;
    }

    public void setSPECIALJOB(String SPECIALJOB) {
        this.SPECIALJOB = SPECIALJOB;
    }

    public String getSTOCKSTIMULATE() {
        return STOCKSTIMULATE;
    }

    public void setSTOCKSTIMULATE(String STOCKSTIMULATE) {
        this.STOCKSTIMULATE = STOCKSTIMULATE;
    }

    public String getFRESHYEAR() {
        return FRESHYEAR;
    }

    public void setFRESHYEAR(String FRESHYEAR) {
        this.FRESHYEAR = FRESHYEAR;
    }

    public String getIDCARD() {
        return IDCARD;
    }

    public void setIDCARD(String IDCARD) {
        this.IDCARD = IDCARD;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String EDUCATION) {
        this.EDUCATION = EDUCATION;
    }

    public String getMAJOR() {
        return MAJOR;
    }

    public void setMAJOR(String MAJOR) {
        this.MAJOR = MAJOR;
    }

    public String getGRADUATEDATE() {
        return GRADUATEDATE;
    }

    public void setGRADUATEDATE(String GRADUATEDATE) {
        this.GRADUATEDATE = GRADUATEDATE;
    }

    public String getENTRYDATE() {
        return ENTRYDATE;
    }

    public void setENTRYDATE(String ENTRYDATE) {
        this.ENTRYDATE = ENTRYDATE;
    }

    public String getQUALIFYDATE() {
        return QUALIFYDATE;
    }

    public void setQUALIFYDATE(String QUALIFYDATE) {
        this.QUALIFYDATE = QUALIFYDATE;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public String getWXID() {
        return WXID;
    }

    public void setWXID(String WXID) {
        this.WXID = WXID;
    }

    public String getHLEADER() {
        return HLEADER;
    }

    public void setHLEADER(String HLEADER) {
        this.HLEADER = HLEADER;
    }

    public String getRANK() {
        return RANK;
    }

    public void setRANK(String RANK) {
        this.RANK = RANK;
    }

    public String getPOSTGG() {
        return POSTGG;
    }

    public void setPOSTGG(String POSTGG) {
        this.POSTGG = POSTGG;
    }

    public String getPUBLIC2() {
        return PUBLIC2;
    }

    public void setPUBLIC2(String PUBLIC2) {
        this.PUBLIC2 = PUBLIC2;
    }
}
