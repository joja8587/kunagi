// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.gen.EntityGenerator










package scrum.server.collaboration;

import java.util.*;
import ilarkesto.persistence.*;
import ilarkesto.logging.*;
import ilarkesto.base.*;
import ilarkesto.base.time.*;
import ilarkesto.auth.*;

public abstract class GComment
            extends AEntity
            implements java.lang.Comparable<Comment> {

    // --- AEntity ---

    public final CommentDao getDao() {
        return commentDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("parentId", this.parentId);
        properties.put("authorId", this.authorId);
        properties.put("text", this.text);
    }

    public int compareTo(Comment other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final Logger LOG = Logger.get(GComment.class);

    public static final String TYPE = "comment";

    // -----------------------------------------------------------
    // - parent
    // -----------------------------------------------------------

    private String parentId;
    private transient scrum.server.project.Requirement parentCache;

    private void updateParentCache() {
        parentCache = this.parentId == null ? null : (scrum.server.project.Requirement)requirementDao.getById(this.parentId);
    }

    public final scrum.server.project.Requirement getParent() {
        if (parentCache == null) updateParentCache();
        return parentCache;
    }

    public final void setParent(scrum.server.project.Requirement parent) {
        parent = prepareParent(parent);
        if (isParent(parent)) return;
        this.parentId = parent == null ? null : parent.getId();
        parentCache = parent;
        fireModified();
    }

    protected scrum.server.project.Requirement prepareParent(scrum.server.project.Requirement parent) {
        return parent;
    }

    protected void repairDeadParentReference(String entityId) {
        if (entityId.equals(this.parentId)) {
            repairMissingMaster();
        }
    }

    public final boolean isParentSet() {
        return this.parentId != null;
    }

    public final boolean isParent(scrum.server.project.Requirement parent) {
        if (this.parentId == null && parent == null) return true;
        return parent != null && parent.getId().equals(this.parentId);
    }

    protected final void updateParent(Object value) {
        setParent(value == null ? null : (scrum.server.project.Requirement)requirementDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - author
    // -----------------------------------------------------------

    private String authorId;
    private transient scrum.server.admin.User authorCache;

    private void updateAuthorCache() {
        authorCache = this.authorId == null ? null : (scrum.server.admin.User)userDao.getById(this.authorId);
    }

    public final scrum.server.admin.User getAuthor() {
        if (authorCache == null) updateAuthorCache();
        return authorCache;
    }

    public final void setAuthor(scrum.server.admin.User author) {
        author = prepareAuthor(author);
        if (isAuthor(author)) return;
        this.authorId = author == null ? null : author.getId();
        authorCache = author;
        fireModified();
    }

    protected scrum.server.admin.User prepareAuthor(scrum.server.admin.User author) {
        return author;
    }

    protected void repairDeadAuthorReference(String entityId) {
        if (entityId.equals(this.authorId)) {
            this.authorId = null;
            fireModified();
        }
    }

    public final boolean isAuthorSet() {
        return this.authorId != null;
    }

    public final boolean isAuthor(scrum.server.admin.User author) {
        if (this.authorId == null && author == null) return true;
        return author != null && author.getId().equals(this.authorId);
    }

    protected final void updateAuthor(Object value) {
        setAuthor(value == null ? null : (scrum.server.admin.User)userDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - text
    // -----------------------------------------------------------

    private java.lang.String text;

    public final java.lang.String getText() {
        return text;
    }

    public final void setText(java.lang.String text) {
        text = prepareText(text);
        if (isText(text)) return;
        this.text = text;
        fireModified();
    }

    protected java.lang.String prepareText(java.lang.String text) {
        text = Str.removeUnreadableChars(text);
        return text;
    }

    public final boolean isTextSet() {
        return this.text != null;
    }

    public final boolean isText(java.lang.String text) {
        if (this.text == null && text == null) return true;
        return this.text != null && this.text.equals(text);
    }

    protected final void updateText(Object value) {
        setText((java.lang.String)value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("parentId")) updateParent(value);
            if (property.equals("authorId")) updateAuthor(value);
            if (property.equals("text")) updateText(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadParentReference(entityId);
        repairDeadAuthorReference(entityId);
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isParentSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getParent();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead parent reference");
            repairDeadParentReference(this.parentId);
        }
        try {
            getAuthor();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead author reference");
            repairDeadAuthorReference(this.authorId);
        }
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    protected static scrum.server.project.RequirementDao requirementDao;

    public static final void setRequirementDao(scrum.server.project.RequirementDao requirementDao) {
        GComment.requirementDao = requirementDao;
    }

    protected static CommentDao commentDao;

    public static final void setCommentDao(CommentDao commentDao) {
        GComment.commentDao = commentDao;
    }

}