package github.caicosantos.concierge.mappers;

import github.caicosantos.concierge.dto.UserRegisterDTO;
import github.caicosantos.concierge.dto.UserResultSearchDTO;
import github.caicosantos.concierge.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRegisterDTO dto);
    UserResultSearchDTO toDTO(User user);
}
