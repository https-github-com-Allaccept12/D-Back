package com.example.dplus.repository.artwork.image;


import com.example.dplus.domain.artwork.ArtWorkImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class ArtWorkImageSaveRepository {

    private final JdbcTemplate jdbcTemplate;
    private final int batchSize = 500;
    public void saveAll(List<ArtWorkImage> imgList) {
        int batchCount = 0;

        //그릇을 만들어주고
        List<ArtWorkImage> bowl = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            bowl.add(imgList.get(i));
            if ((i + 1) % batchSize == 0) {
                //배치사이즈가 되면 전체 인서트
                batchCount = batchInsert(batchCount,bowl);
            }
        }
        if (!bowl.isEmpty()) {
            //배치 사이즈(500)를 못채우고 나오거나, 총데이터가 505개라 5개가 남으면 남은것 인서트
            batchCount = batchInsert(batchCount,bowl);
        }

        log.info(String.valueOf(batchCount));

    }
    private int batchInsert(int batchCount, List<ArtWorkImage> bowl) {
        jdbcTemplate.batchUpdate("insert into art_work_image(`artwork_img`, `artwork_id`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    // TODO 직접 insert 하기 때문에 재설정 필요
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
        return ++batchCount;
    }

}
