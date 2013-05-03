/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package com.steeplesoft.frenchpress.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author jdlee
 */
@Entity
@Table(name="attachments")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="seq_gen", table = "sequence", pkColumnName = "seq_name", valueColumnName = "seq_count")
    @GeneratedValue(generator="seq_gen", strategy = GenerationType.TABLE)
    private Long id;
    private String name;
    private String mimeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
