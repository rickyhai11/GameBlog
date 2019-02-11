package services.interfaces;

import datamodels.dto.CommentDTO;

import java.util.List;
import java.util.Optional;

public interface CommentServiceInf {

    CommentDTO saveComment(CommentDTO commentDTO);

    Optional<List<CommentDTO>> findCommentsForArticle(Long postId);

}
