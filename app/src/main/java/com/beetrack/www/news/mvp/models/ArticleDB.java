package com.beetrack.www.news.mvp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class ArticleDB {

    @Id(autoincrement = true)
    private Long id;

    private long sourceId;

    @ToOne(joinProperty = "sourceId")
    private SourceDB source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 451226800)
    private transient ArticleDBDao myDao;

    @Generated(hash = 780329374)
    private transient Long source__resolvedKey;

    public ArticleDB() {
    }

    @Generated(hash = 2005393456)
    public ArticleDB(Long id, long sourceId, String author, String title,
            String description, String url, String urlToImage, String publishedAt) {
        this.id = id;
        this.sourceId = sourceId;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 424740332)
    public SourceDB getSource() {
        long __key = this.sourceId;
        if (source__resolvedKey == null || !source__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SourceDBDao targetDao = daoSession.getSourceDBDao();
            SourceDB sourceNew = targetDao.load(__key);
            synchronized (this) {
                source = sourceNew;
                source__resolvedKey = __key;
            }
        }
        return source;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 817339436)
    public void setSource(@NotNull SourceDB source) {
        if (source == null) {
            throw new DaoException(
                    "To-one property 'sourceId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.source = source;
            sourceId = source.getId();
            source__resolvedKey = sourceId;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1825595880)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getArticleDBDao() : null;
    }

}
