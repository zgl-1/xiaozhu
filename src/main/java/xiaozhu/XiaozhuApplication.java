package xiaozhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "xiaozhu.mapper")
public class XiaozhuApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaozhuApplication.class, args);
	}

}
