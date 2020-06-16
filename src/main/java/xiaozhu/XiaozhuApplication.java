package xiaozhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "xiaozhu.mapper")
@EnableCaching
public class XiaozhuApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiaozhuApplication.class, args);
	}

}
