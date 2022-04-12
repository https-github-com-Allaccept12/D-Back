package com.example.dplus.repository;


import com.example.dplus.domain.artwork.ArtWorkImage;
import com.example.dplus.domain.post.PostImage;
import com.example.dplus.domain.post.PostTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("batchSize")
    private final int batchSize;

    public void artWorkImageSaveAll(List<ArtWorkImage> imgList) {
        List<ArtWorkImage> bowl = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            bowl.add(imgList.get(i));
            if ((i + 1) % batchSize == 0) {
                //배치사이즈가 되면 전체 인서트
                artWorkImageBatchInsert(bowl);
            }
        }
        //배치 사이즈(500)를 못채우고 나오거나, 총데이터가 505개라 5개가 남으면 남은것 인서트
        if (!bowl.isEmpty()) {
            artWorkImageBatchInsert(bowl);
        }
    }
    public void postImageSaveAll(List<PostImage> imgList) {
        List<PostImage> bowl = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            bowl.add(imgList.get(i));
            if ((i + 1) % batchSize == 0) {
                //배치사이즈가 되면 전체 인서트
                postImageBatchInsert(bowl);
            }
        }

        //배치 사이즈(500)를 못채우고 나오거나, 총데이터가 505개라 5개가 남으면 남은것 인서트
        if (!bowl.isEmpty()) {
            postImageBatchInsert(bowl);
        }
    }
    public void postTagImageSaveAll(List<PostTag> tagList) {
        List<PostTag> bowl = new ArrayList<>();
        for (int i = 0; i < tagList.size(); i++) {
            bowl.add(tagList.get(i));
            if ((i + 1) % batchSize == 0) {
                //배치사이즈가 되면 전체 인서트
                postTagBatchInsert(bowl);
            }
        }

        //배치 사이즈(500)를 못채우고 나오거나, 총데이터가 505개라 5개가 남으면 남은것 인서트
        if (!bowl.isEmpty()) {
            postTagBatchInsert(bowl);
        }
    }

    private void postTagBatchInsert(List<PostTag> bowl) {
        jdbcTemplate.batchUpdate("insert into post_tag(`hash_tag`, `post_id`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, bowl.get(i).getHashTag());
                        ps.setString(2, String.valueOf(bowl.get(i).getPost().getId()));
                    }
                    @Override
                    public int getBatchSize() {
                        return bowl.size();
                    }
                });
        bowl.clear();
    }

    private void postImageBatchInsert(List<PostImage> bowl) {
        jdbcTemplate.batchUpdate("insert into post_image(`post_img`, `post_id`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, bowl.get(i).getPostImg());
                        ps.setString(2, String.valueOf(bowl.get(i).getPost().getId()));
                    }
                    @Override
                    public int getBatchSize() {
                        return bowl.size();
                    }
                });
        bowl.clear();
    }
    private void artWorkImageBatchInsert(List<ArtWorkImage> bowl) {
        jdbcTemplate.batchUpdate("insert into art_work_image(`artwork_img`, `artwork_id`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, bowl.get(i).getArtworkImg());
                        ps.setString(2, String.valueOf(bowl.get(i).getArtWorks().getId()));
                    }
                    @Override
                    public int getBatchSize() {
                        return bowl.size();
                    }
                });
        bowl.clear();
    }


}
