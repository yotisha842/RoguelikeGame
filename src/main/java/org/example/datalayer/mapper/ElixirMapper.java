package org.example.datalayer.mapper;

import org.example.datalayer.dto.ElixirDTO;
import org.example.domain.entity.item.Elixir;
import org.example.domain.entity.game.Coordinate;
import org.example.domain.entity.item.Effect.Type;

public class ElixirMapper extends BaseMapper {

    public ElixirDTO toDto(Elixir elixir) {
        if (elixir == null) return null;

        return new ElixirDTO(
                elixir.getType(),
                toCoordinateDTO(elixir.getPosition()),
                elixir.getElixirType().toString(),
                elixir.getValue(),
                elixir.getDuration()
        );
    }

    public Elixir toEntity(ElixirDTO dto) {
        if (dto == null) return null;

        Type elixirType = Type.valueOf(dto.getEffectType());
        Coordinate position = toCoordinate(dto.getPosition());

        Elixir elixir = new Elixir(position, elixirType, dto.getValue(), dto.getDuration());
        elixir.setType(dto.getType());
        return elixir;
    }
}