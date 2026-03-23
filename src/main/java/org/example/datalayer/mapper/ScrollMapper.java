package org.example.datalayer.mapper;

import org.example.datalayer.dto.ScrollDTO;
import org.example.domain.entity.item.Scroll;
import org.example.domain.entity.game.Coordinate;

public class ScrollMapper extends BaseMapper {

    public ScrollDTO toDto(Scroll scroll) {
        if (scroll == null) return null;

        return new ScrollDTO(
                scroll.getType(),
                toCoordinateDTO(scroll.getPosition()),
                scroll.getScrollType().toString()
        );
    }

    public Scroll toEntity(ScrollDTO dto) {
        if (dto == null) return null;

        Scroll.ScrollType scrollType = Scroll.ScrollType.valueOf(dto.getScrollType());
        Coordinate position = toCoordinate(dto.getPosition());

        Scroll scroll = new Scroll(position, scrollType);
        scroll.setType(dto.getType());
        return scroll;
    }
}