package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Todo {
    @Id
    private long id;

    private String action;//string	no	The action to be filtered. Can be assigned, mentioned, build_failed, marked, approval_required, unmergeable, directly_addressed or merge_train_removed.
    private String author_id;//	integer	no	The ID of an author
    private String project_id;//	integer	no	The ID of a project
    private String group_id;//	integer	no	The ID of a group
    private String state;//	string	no	The state of the to-do item. Can be either pending or done
    private String type;//	string	no	The type of to-do item. Can be either Issue, MergeRequest, Commit, Epic, DesignManagement::Design or AlertManagement::Alert
}
