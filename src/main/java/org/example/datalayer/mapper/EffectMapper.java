package org.example.datalayer.mapper;

import org.example.datalayer.dto.EffectDTO;
import org.example.domain.entity.item.Effect;
import org.example.domain.entity.item.Effect.Type;
import java.util.List;
import java.util.ArrayList;

public class EffectMapper extends BaseMapper {

    public EffectDTO toDto(Effect effect) {
        if (effect == null) return null;

        return new EffectDTO(
                effect.getType().toString(),
                effect.getValue(),
                effect.getCounter()
        );
    }

    public Effect toEntity(EffectDTO dto) {
        if (dto == null) return null;

        Type type = Type.valueOf(dto.getType());
        return new Effect(type, dto.getValue(), dto.getCounter());
    }

    public List<EffectDTO> toDtoList(List<Effect> effects) {
        if (effects == null) return null;

        List<EffectDTO> dtos = new ArrayList<>();
        for (Effect effect : effects) {
            dtos.add(toDto(effect));
        }
        return dtos;
    }

    public List<Effect> toEntityList(List<EffectDTO> dtos) {
        if (dtos == null) return null;

        List<Effect> effects = new ArrayList<>();
        for (EffectDTO dto : dtos) {
            effects.add(toEntity(dto));
        }
        return effects;
    }
}