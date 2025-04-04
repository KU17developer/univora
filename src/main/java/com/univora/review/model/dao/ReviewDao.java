package com.univora.review.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.univora.review.model.dto.Review;

public class ReviewDao {
	
	    public int insertReview(SqlSession session, Review review) {
	        return session.insert("review.insertReview", review);
	    }

	    
	    // 대댓글 처리
	    public int insertReply(SqlSession session, Review reply) {
	        return session.insert("review.insertReply", reply);
	    }
	    
	    // 강의 점수 가져오기
	    public double getAverageRating(SqlSession session, String lectureNo) {
	        return session.selectOne("review.getAverageRating", lectureNo);
	    }
	    
	    // 좋아요 기능 구현
	    public int checkIfLiked(SqlSession session, String reviewNo, String memberNo) {
	        int likeCount = session.selectOne("review.checkIfLiked", 
	            Map.of("reviewNo", reviewNo, "memberNo", memberNo));
	        return likeCount;
	    }
	    
	    public int getLikeCount(SqlSession session, String reviewNo) {
	        return session.selectOne("review.getLikeCount", reviewNo);
	    }
	    
	    
	    public boolean isLiked(SqlSession session, String reviewNo, String memberNo) {
	        // MyBatis를 사용하여 좋아요 여부 확인
	        Integer count = session.selectOne("review.isLiked", Map.of("reviewNo", reviewNo, "memberNo", memberNo));
	        return count != null && count > 0;
	    }
	    
	    
	    public int removeLike(SqlSession session, String reviewNo, String memberNo) {
	        // 좋아요 삭제 로직
	        return session.delete("review.removeLike", Map.of("reviewNo", reviewNo, "memberNo", memberNo));
	        
	    }
	    
	    public int decreseLike(SqlSession session, String lectureNo) {
	    	return session.update("review.decreaseLikeCount", lectureNo);
	    }
	    
	    
	    public int addLike(SqlSession session, String reviewNo, String memberNo) {
	        // 좋아요 추가 로직
	        return session.insert("review.addLike", Map.of("reviewNo", reviewNo, "memberNo", memberNo));
	        
	    }
	    
	    
	    public int increaseLikeCount(SqlSession session, String lectureNo) {
	    	return session.update("review.increaseLikeCount", lectureNo);
	    }
	    
	    
	    public int deleteReview(SqlSession session, String reviewNo) {
	        return session.update("review.deleteReview", reviewNo);
	    }

	    
	    public String getLectureNoByReviewNo(SqlSession session, String reviewNo) {
	        return session.selectOne("review.getLectureNoByReviewNo", reviewNo);
	    }

	    
	    // 작성한 수강평을 가져오자!
	    public List<Review> getUserReviews(SqlSession session, String studentNo) {
	        return session.selectList("review.getUserReviews", studentNo);
	    }


	 // 부모 댓글 삭제 (is_deleted = 'Y')
	    public int updateIsDeletedFlag(SqlSession session, String reviewNo) {
	        return session.update("review.updateIsDeletedFlag", reviewNo);
	    }

	    // 자식 댓글 삭제 (is_deleted = 'Y')
	    public int updateChildReviewsFlag(SqlSession session, String parentReviewNo) {
	        return session.update("review.updateChildReviewsFlag", parentReviewNo);
	    }
	    
	    public Review getReviewById(SqlSession session, String reviewNo) {
	        return session.selectOne("review.getReviewById", reviewNo);
	    }

	    public int updateReview(SqlSession session, String reviewNo, String reviewContent) {
	        
	  		Review review = Review.builder()
	  	            .reviewNo(reviewNo)
	  	            .reviewContent(reviewContent)
	  	            .build();
	  		
	        return session.update("review.updateReview", review);
	    }
		public Map<String, Object> selectReviewAuthor(SqlSession session, String reviewNo) {
			return session.selectOne("review.selectReviewAuthor", reviewNo);
		}
		
		
		public Review getReplyById(SqlSession session, String reviewNo) {
		    return session.selectOne("review.getReplyById", reviewNo);
		}

		public int updateReply(SqlSession session, String reviewNo, String reviewContent) {
		    Map<String, Object> params = new HashMap<>();
		    params.put("reviewNo", reviewNo);
		    params.put("reviewContent", reviewContent);
		    return session.update("review.updateReply", params);
		}

		  
}
