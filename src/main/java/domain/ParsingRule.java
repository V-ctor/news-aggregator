package domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Data @Accessors(chain = true)
public class ParsingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @OneToOne
    @Valid
    @NotEmpty
    private NewsResource newsResource;
    private String itemDomType;
    private String itemDomValue;
    private String captionDomType;
    private String captionDomValue;
    private String articleUrlDomType;
    private String articleUrlDomValue;
}
