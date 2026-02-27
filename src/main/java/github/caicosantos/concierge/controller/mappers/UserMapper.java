package github.caicosantos.concierge.controller.mappers;

import github.caicosantos.concierge.controller.dto.UserRegisterDTO;
import github.caicosantos.concierge.controller.dto.UserResultSearchDTO;
import github.caicosantos.concierge.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRegisterDTO dto);
    UserResultSearchDTO toDTO(User user);
}
