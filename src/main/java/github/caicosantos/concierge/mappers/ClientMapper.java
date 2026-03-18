package github.caicosantos.concierge.mappers;

import github.caicosantos.concierge.dto.ClientRegisterDTO;
import github.caicosantos.concierge.dto.ClientResultSearchDTO;
import github.caicosantos.concierge.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientRegisterDTO dto);
    ClientResultSearchDTO toDTO(Client client);
}
