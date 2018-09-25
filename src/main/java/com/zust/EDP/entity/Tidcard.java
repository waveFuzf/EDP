package com.zust.EDP.entity;

import sun.security.krb5.internal.Ticket;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "idcard")
public class Tidcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String idcardnum;
    @Column
    private String realname;

    // 与Tuser关联
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tidcard")
    private Set<Tuser> userId = new HashSet<Tuser>();

    public Tidcard(){

    }

    public Tidcard(String cardnum, String realname) {
        this.idcardnum=cardnum;
        this.realname=realname;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdcardnum() {
        return idcardnum;
    }

    public void setIdcardnum(String idcardnum) {
        this.idcardnum = idcardnum;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

}

