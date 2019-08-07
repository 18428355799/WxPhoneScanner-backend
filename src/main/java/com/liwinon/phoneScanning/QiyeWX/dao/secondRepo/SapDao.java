package com.liwinon.phoneScanning.QiyeWX.dao.secondRepo;


import com.liwinon.phoneScanning.QiyeWX.entity.second.Sap_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SapDao extends JpaRepository<Sap_Users,String> {
    @Query(value = "select s from Sap_Users s where s.PERSONID = :PERSONID")
    Sap_Users findByPERSONID(String PERSONID);
    @Query(value = "select s.NAME from Sap_Users s where s.PERSONID = :PERSONID")
    String findNByUserId(String PERSONID);

    @Query(value = "select  s.PERSONID from Sap_Users s where s.SAP_ID = :sapid")
    String  findPersonidBySAP_ID(String sapid);

    @Query(value = "select s.RANK from Sap_Users s where s.PERSONID = :PERSONID and" +
            " s.GROUPTXT <> '离职员工'")
    String findRankByPERSONID(String PERSONID);

    //获取leader sapid
    @Query(value = "select s.HLEADER from Sap_Users s WHERE s.PERSONID = :PERSONID and" +
            " s.GROUPTXT <> '离职员工'")
    String findSAPIDByPERSONID(String PERSONID);
}
