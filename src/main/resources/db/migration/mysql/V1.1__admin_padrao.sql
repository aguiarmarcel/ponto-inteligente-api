INSERT INTO `empresa` (`id`, `cnpj`, `data_atualizacao`, `data_criacao`, `razao_social` )
VALUES (NULL, '82198127000121', CURRENT_DATE(), CURRENT_DATE(), 'Vá de Bazar');

INSERT INTO `funcionario` (`id`, `cpf`, `data_atualizacao`, `data_criacao`, `email`, `nome`, 
`perfil`, `qtd_horas_trabalho_dia`, `qtd_horas_almoco`, `senha`, `valor_hora`, `empresa_id`)
VALUES (NULL, '16248890935', CURRENT_DATE(), CURRENT_DATE(), 'aguiar_marcel@hotmail.com', 'ADMIN', 'ROLE_ADMIN', NULL, NULL,
'$2a$06$xIvBeNRfS65L1N17I7JzgefzxEuLAL0Xk0wFAgIkoNqu9WD6rmp4m', NULL,(SELECT `id` FROM `empresa` where `cnpj` = '82198127000121'));

