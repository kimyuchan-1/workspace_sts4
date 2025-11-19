package edu.pnu.domain;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
	private Integer id;
	private String pass;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date regidate;
}
