package com.example.data.model

enum class Difficulty(val label: String, val points: Int) {
    FACIL("Fácil", 100),
    MEDIA("Média", 150),
    DIFICIL("Difícil", 200)
}

data class Question(
    val id: Int,
    val category: String, // e.g., "NR-01", "NR-05", "Primeiros Socorros", etc.
    val difficulty: Difficulty,
    val scenario: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

object QuestionBank {
    val questions: List<Question> = listOf(
        Question(
            id = 1,
            category = "NR-01 (GRO/PGR)",
            difficulty = Difficulty.FACIL,
            scenario = "Em uma inspeção de rotina na marcenaria, o Técnico em Segurança do Trabalho observa serragem acumulada no chão e ruído elevado.",
            prompt = "No Programa de Gerenciamento de Riscos (PGR), qual documento deve registrar a relação desses perigos com suas medidas de controle?",
            options = listOf(
                "Inventário de Riscos Ocupacionais e Plano de Ação",
                "Apenas o Manual de Instruções das Máquinas",
                "Livro de ponto dos funcionários",
                "Certificado de Conclusão de Curso do Operador"
            ),
            correctIndex = 0,
            explanation = "A NR-01 estabelece que o PGR deve ser composto no mínimo por Inventário de Riscos e Plano de Ação estruturado."
        ),
        Question(
            id = 2,
            category = "NR-05 (CIPA)",
            difficulty = Difficulty.FACIL,
            scenario = "A CIPA do Senac Vila Prudente está planejando suas atividades semestrais voltadas aos colaboradores e discentes.",
            prompt = "Qual é o principal objetivo da Comissão Interna de Prevenção de Acidentes e Assédio (CIPA)?",
            options = listOf(
                "Tornar compatível permanentemente o trabalho com a preservação da vida e a promoção da saúde do trabalhador",
                "Aplicar penalidades financeiras imediatas aos funcionários que não usam EPI",
                "Substituir o departamento jurídico da instituição em disputas trabalhistas",
                "Comprar materiais de escritório e aprovar orçamentos da diretoria"
            ),
            correctIndex = 0,
            explanation = "O objetivo fundamental da NR-05 é a prevenção de acidentes e doenças decorrentes do trabalho e a promoção de um ambiente saudável e seguro."
        ),
        Question(
            id = 3,
            category = "NR-06 (EPI)",
            difficulty = Difficulty.FACIL,
            scenario = "Um técnico precisa indicar proteção para um trabalhador que atuará em ambiente com projeção de partículas volantes sólidas.",
            prompt = "Para que um Equipamento de Proteção Individual seja comercializado e utilizado legalmente no Brasil, ele deve possuir obrigatoriamente:",
            options = listOf(
                "Certificado de Aprovação (CA) válido expedido pelo órgão competente",
                "Etiqueta impressa pelo fabricante sem necessidade de testes laboratoriais",
                "Apenas a nota fiscal de compra emitida em território nacional",
                "Selo de garantia estendida de 5 anos fornecido pela loja revendedora"
            ),
            correctIndex = 0,
            explanation = "A NR-06 determina que todo EPI deve possuir o CA (Certificado de Aprovação) emitido pelo Ministério do Trabalho e Emprego."
        ),
        Question(
            id = 4,
            category = "Primeiros Socorros",
            difficulty = Difficulty.FACIL,
            scenario = "Um colega de laboratório escorrega, bate a cabeça e fica inconsciente, porém respira normalmente e não apresenta fratura exposta.",
            prompt = "Qual deve ser a primeira atitude do socorrista leigo treinado enquanto aguarda o socorro especializado (SAMU/192)?",
            options = listOf(
                "Avaliar a segurança da cena, acionar o serviço de emergência e manter as vias aéreas desobstruídas sem movimentar a vítima desnecessariamente",
                "Oferecer água com açúcar e sacudir a vítima vigorosamente para que acorde",
                "Levantar a vítima pelas costas e fazê-la caminhar para ativar a circulação",
                "Dar tapas no rosto e esfregar álcool nos pulsos da pessoa inconsciente"
            ),
            correctIndex = 0,
            explanation = "Em primeiros socorros, proteger a coluna cervical, garantir vias aéreas pérvias e acionar o suporte especializado são regras básicas vitais."
        ),
        Question(
            id = 5,
            category = "NR-10 (Segurança em Eletricidade)",
            difficulty = Difficulty.FACIL,
            scenario = "Um eletricista precisa realizar a manutenção preventiva em um quadro de distribuição de baixa tensão.",
            prompt = "Conforme a NR-10, qual é a sequência correta para considerar uma instalação elétrica desenergizada e liberada para trabalho seguro?",
            options = listOf(
                "Seccionamento, impedimento de reenergização, constatação de ausência de tensão, aterramento temporário, proteção dos elementos energizados e sinalização",
                "Apenas desligar o disjuntor principal e pedir aos colegas para não religarem",
                "Usar luvas de borracha doméstica e encostar os dedos para testar choque",
                "Trabalhar sempre com o circuito energizado para agilizar a entrega do serviço"
            ),
            correctIndex = 0,
            explanation = "A desenergização na NR-10 segue 6 passos rigorosos: seccionamento, bloqueio, teste de ausência de tensão, aterramento temporário, equipotencialização e sinalização."
        ),
        Question(
            id = 6,
            category = "NR-17 (Ergonomia)",
            difficulty = Difficulty.FACIL,
            scenario = "Em um posto de atendimento informatizado, o monitor está posicionado muito abaixo do nível dos olhos do operador, causando dores cervicais.",
            prompt = "Segundo a NR-17 e os princípios da Ergonomia, qual adequação deve ser realizada?",
            options = listOf(
                "Ajustar a altura da tela de modo que a borda superior fique aproximadamente no nível dos olhos, com teclado e assento reguláveis",
                "Obrigar o trabalhador a inclinar o tronco para a frente durante toda a jornada",
                "Fixar o monitor no teto para alongar a coluna",
                "Substituir a cadeira por um banco de madeira rígido sem encosto"
            ),
            correctIndex = 0,
            explanation = "A NR-17 preconiza a adaptação das condições de trabalho às características psicofisiológicas dos trabalhadores, garantindo conforto e postura neutra."
        ),
        Question(
            id = 7,
            category = "Prevenção de Acidentes",
            difficulty = Difficulty.FACIL,
            scenario = "Durante uma ronda matinal, o técnico observa que uma saída de emergência está bloqueada com caixas de papelão.",
            prompt = "Essa situação é classificada como:",
            options = listOf(
                "Condição perigosa/insegura do ambiente que compromete as rotas de fuga e evacuação",
                "Uma estratégia de armazenamento eficiente e aprovada pelos bombeiros",
                "Risco puramente estético que não afeta a segurança das pessoas",
                "Procedimento padrão para evitar correntes de vento no corredor"
            ),
            correctIndex = 0,
            explanation = "Rotas de fuga e saídas de emergência desobstruídas são exigências primárias de prevenção e proteção contra incêndios e pânico."
        ),
        Question(
            id = 8,
            category = "NR-07 (PCMSO)",
            difficulty = Difficulty.FACIL,
            scenario = "Um novo colaborador foi contratado e iniciará suas funções operacionais na próxima semana.",
            prompt = "Qual exame médico ocupacional previsto na NR-07 deve ser realizado antes que o trabalhador assuma suas atividades?",
            options = listOf(
                "Exame Médico Admissional",
                "Exame Médico Demissional",
                "Exame de Retorno ao Trabalho",
                "Exame Periódico Anual obrigatório apenas aos 5 anos de casa"
            ),
            correctIndex = 0,
            explanation = "O exame admissional da NR-07 deve ser obrigatoriamente realizado antes que o trabalhador inicie suas funções operacionais."
        ),
        Question(
            id = 9,
            category = "NR-09 (Agentes Ambientais)",
            difficulty = Difficulty.FACIL,
            scenario = "Em uma oficina mecânica, os trabalhadores utilizam desengraxantes químicos à base de solventes orgânicos inflamáveis.",
            prompt = "A NR-09 trata da avaliação e controle das exposições ocupacionais a quais tipos de agentes ambientais?",
            options = listOf(
                "Agentes Físicos, Químicos e Biológicos",
                "Apenas problemas de relacionamento interpessoal",
                "Exclusivamente flutuações financeiras da empresa",
                "Tributos fiscais e fiscais contábeis"
            ),
            correctIndex = 0,
            explanation = "A NR-09 foca na identificação, avaliação e controle das exposições ocupacionais aos agentes físicos, químicos e biológicos no ambiente de trabalho."
        ),
        Question(
            id = 10,
            category = "Investigação de Acidentes",
            difficulty = Difficulty.FACIL,
            scenario = "Ocorreu uma queda em mesmo nível no refeitório, sem fratura, mas com contusão leve no joelho.",
            prompt = "Por que os técnicos de SST devem investigar acidentes leves e quase-acidentes com a mesma seriedade de acidentes graves?",
            options = listOf(
                "Porque os quase-acidentes revelam falhas latentes no sistema que podem gerar acidentes fatais no futuro se não corrigidas",
                "Para poder demitir imediatamente o colaborador envolvido",
                "Apenas para cumprir burocracia de arquivo morto sem gerar ações reais",
                "Para calcular desconto no salário do operador que tropeçou"
            ),
            correctIndex = 0,
            explanation = "A pirâmide de segurança e a teoria moderna de acidentes mostram que controlar incidentes e desvios leves previne tragédias graves."
        ),
        Question(
            id = 11,
            category = "NR-01 (GRO/PGR)",
            difficulty = Difficulty.MEDIA,
            scenario = "Ao elaborar o inventário de riscos, a equipe multidisciplinar de SST analisa o risco de prensamento de dedos em uma prensa hidráulica.",
            prompt = "Na matriz de risco da NR-01, o nível de risco ocupacional é determinado fundamentalmente pela combinação de quais dois fatores?",
            options = listOf(
                "Severidade das possíveis lesões ou agravos à saúde e Probabilidade de ocorrência desses eventos",
                "Preço da máquina no mercado e custo do plano de saúde empresarial",
                "Idade do operador multiplicada pelo tempo de serviço no setor",
                "Número de advertências aplicadas pela chefia nos últimos 12 meses"
            ),
            correctIndex = 0,
            explanation = "A NR-01 define o nível de risco ocupacional como a combinação da severidade do dano com a probabilidade de sua ocorrência."
        ),
        Question(
            id = 12,
            category = "NR-18 (Construção Civil)",
            difficulty = Difficulty.MEDIA,
            scenario = "No Senac, está ocorrendo uma reforma predial na fachada externa com andaimes suspensos a 6 metros de altura.",
            prompt = "Segundo a NR-18 e a NR-35, qual medida de proteção coletiva ou individual é mandatória para trabalhos acima de 2 metros de altura com risco de queda?",
            options = listOf(
                "Instalação de Sistema de Proteção Coletiva Contra Quedas (SPCQ), guarda-corpo rígido e uso de cinto tipo paraquedista ancorado em linha de vida calculada",
                "Trabalhar apenas em dias sem chuva utilizando tênis esportivo comum",
                "Segurar com uma mão no andaime e trabalhar com a outra livre",
                "Subir no andaime apenas com auxílio de escadas improvisadas de madeira"
            ),
            correctIndex = 0,
            explanation = "Trabalhos em altura exigem proteções coletivas prioritárias (guarda-corpos) e EPI (cinto de segurança tipo paraquedista com talabarte duplo ancorado)."
        ),
        Question(
            id = 13,
            category = "NR-06 (Hierarquia de Controle)",
            difficulty = Difficulty.MEDIA,
            scenario = "Uma serra circular emite ruído contínuo de 92 dB(A), acima do limite de tolerância estabelecido na NR-15.",
            prompt = "Qual deve ser a ordem prioritária de aplicação das medidas de controle de segurança segundo as Normas Regulamentadoras?",
            options = listOf(
                "1º Eliminação do perigo; 2º Medidas de proteção coletiva (EPC); 3º Medidas administrativas; 4º EPI por último",
                "1º Entregar protetor auricular barato e esquecer as proteções de máquina",
                "1º Demitir quem reclama do barulho e depois comprar protetores auriculares",
                "1º Medidas administrativas e nunca usar proteção coletiva em máquinas"
            ),
            correctIndex = 0,
            explanation = "A hierarquia das medidas de controle prioriza a eliminação e proteção coletiva (EPC) na fonte, deixando o EPI como medida complementar residual."
        ),
        Question(
            id = 14,
            category = "Comportamento Seguro",
            difficulty = Difficulty.MEDIA,
            scenario = "Um operador experiente decide pular a etapa de travamento de energia para 'ganhar 5 minutos' e bater a meta de produção.",
            prompt = "Esse comportamento exemplifica qual conceito clássico em fatores humanos na segurança do trabalho?",
            options = listOf(
                "Normalização do desvio, onde o risco tolerado repetidamente sem acidentes gera falsa sensação de imunidade e segurança",
                "Proatividade exemplar que deve ser premiada pela diretoria da fábrica",
                "Cumprimento perfeito dos prazos industriais sem reflexo em SST",
                "Ato heroico individual que dispensa normas técnicas de engenharia"
            ),
            correctIndex = 0,
            explanation = "A normalização do desvio ocorre quando práticas arriscadas tornam-se rotina porque 'nunca deu nada', até que a tragédia aconteça."
        ),
        Question(
            id = 15,
            category = "Riscos Psicossociais",
            difficulty = Difficulty.MEDIA,
            scenario = "A equipe de atendimento relata sobrecarga contínua, metas inatingíveis, ameaças de demissão e isolamento social no trabalho.",
            prompt = "No contexto atual de Saúde e Segurança do Trabalho, como devem ser tratados os fatores de risco psicossociais?",
            options = listOf(
                "Devem ser mapeados no PGR/GRO, prevenindo assédio, Síndrome de Burnout e adoecimento mental coletivo",
                "São fraquezas emocionais individuais que não dizem respeito à gestão de SST da empresa",
                "Devem ser ignorados pela CIPA pois não causam sangramento visível",
                "São problemas estritamente domésticos que se resolvem fora do ambiente de trabalho"
            ),
            correctIndex = 0,
            explanation = "Fatores psicossociais causam transtornos mentais e afetam a segurança física; sua gestão integrada é diretriz essencial em SST moderna."
        ),
        Question(
            id = 16,
            category = "NR-10 (Prontuário das Instalações)",
            difficulty = Difficulty.MEDIA,
            scenario = "Uma edificação comercial possui carga instalada superior a 75 kW no seu centro de medição.",
            prompt = "Qual documento exigido pela NR-10 reúne diagramas unifilares, laudos de aterramento e certificações de equipamentos?",
            options = listOf(
                "Prontuário de Instalações Elétricas (PIE)",
                "Alvará de funcionamento da prefeitura apenas",
                "Contrato de prestação de serviços de limpeza predial",
                "Manual do usuário de aparelhos de ar-condicionado"
            ),
            correctIndex = 0,
            explanation = "O Prontuário de Instalações Elétricas (PIE) é obrigatório pela NR-10 para estabelecimentos com carga instalada superior a 75 kW."
        ),
        Question(
            id = 17,
            category = "Primeiros Socorros (Hemorragia)",
            difficulty = Difficulty.MEDIA,
            scenario = "Um trabalhador sofre um corte profundo no antebraço com lâmina de estilete, apresentando sangramento ativo pulsátil vermelho vivo.",
            prompt = "Qual é a intervenção inicial prioritária para conter essa hemorragia externa?",
            options = listOf(
                "Aplicar compressão direta sobre a ferida com pano limpo ou gaze estéril, mantendo pressão firme e contínua",
                "Passar pomada anti-inflamatória e tampar com fita crepe comum",
                "Lavar abundantemente com sabão em pó e esfregar com escova rígida",
                "Fazer cortes adicionais para drenar o excesso de sangue do membro"
            ),
            correctIndex = 0,
            explanation = "A compressão direta sobre o ponto de sangramento com curativo limpo e pressão contínua é a manobra padrão prioritária no controle de hemorragias."
        ),
        Question(
            id = 18,
            category = "Identificação de Riscos (Mapa de Risco)",
            difficulty = Difficulty.MEDIA,
            scenario = "A CIPA da unidade está atualizando a representação visual dos riscos nos laboratórios de química e manutenção.",
            prompt = "Na metodologia clássica do Mapa de Risco da CIPA, quais cores representam os riscos Químicos, Físicos e Biológicos, respectivamente?",
            options = listOf(
                "Vermelho (Químico), Verde (Físico) e Marrom (Biológico)",
                "Azul (Químico), Amarelo (Físico) e Preto (Biológico)",
                "Verde (Químico), Roxo (Físico) e Cinza (Biológico)",
                "Laranja (Químico), Rosa (Físico) e Branco (Biológico)"
            ),
            correctIndex = 0,
            explanation = "A padronização das cores no Mapa de Riscos define: Verde (Físicos), Vermelho (Químicos), Marrom (Biológicos), Amarelo (Ergonômicos) e Azul (Acidentes/Mecânicos)."
        ),
        Question(
            id = 19,
            category = "NR-05 (Estabilidade CIPA)",
            difficulty = Difficulty.MEDIA,
            scenario = "Um funcionário eleito pelos empregados como titular da CIPA teme ser dispensado arbitrariamente após fiscalizar a área fabril.",
            prompt = "Qual garantia legal a legislação trabalhista e a NR-05 conferem aos representantes dos empregados eleitos na CIPA?",
            options = listOf(
                "Estabilidade provisória no emprego desde o registro da candidatura até um ano após o término do mandato, ressalvada a justa causa",
                "Aposentadoria imediata com salário integral pago pela previdência",
                "Direito de não cumprir jornada de trabalho na empresa",
                "Nenhuma garantia, podendo ser demitido sem justa causa a qualquer momento"
            ),
            correctIndex = 0,
            explanation = "Membros eleitos titulares e suplentes têm estabilidade provisória do registro da candidatura até 1 ano após o fim do mandato contra demissão arbitrária."
        ),
        Question(
            id = 20,
            category = "Investigação de Acidentes (Diagrama de Ishikawa)",
            difficulty = Difficulty.MEDIA,
            scenario = "Após uma colisão entre duas empilhadeiras no centro logístico, a comissão de SST inicia uma reunião de análise de causas.",
            prompt = "Qual ferramenta de qualidade e investigação de SST auxilia na categorização das causas em Método, Máquina, Mão de obra, Material, Meio ambiente e Medida (6M)?",
            options = listOf(
                "Diagrama de Causa e Efeito (Espinha de Peixe ou Diagrama de Ishikawa)",
                "Matriz SWOT para marketing institucional",
                "Gráfico de balanço patrimonial contábil",
                "Folha de cálculo de comissões de vendas de produtos"
            ),
            correctIndex = 0,
            explanation = "O Diagrama de Ishikawa (espinha de peixe) estrutura a investigação de acidentes analisando as causas-raiz nas diferentes dimensões produtivas."
        ),
        Question(
            id = 21,
            category = "NR-01 (Direito de Recusa)",
            difficulty = Difficulty.DIFICIL,
            scenario = "Dois operadores são instruídos a descer em um tanque de contenção com forte odor de gases sem equipamento de ventilação ou detector multigás.",
            prompt = "Conforme assegurado pela NR-01, qual é o direito fundamental do trabalhador diante dessa situação de grave e iminente risco?",
            options = listOf(
                "Exercer o Direito de Recusa, interrompendo suas atividades e comunicando imediatamente o superior hierárquico, sem sofrer prejuízos ou represálias",
                "Cumprir a ordem sem reclamar e torcer para não desmaiar dentro do tanque",
                "Abandonar a empresa e mudar de cidade sem prestar esclarecimentos",
                "Pedir para estagiários entrarem primeiro para testar a toxicidade do ar"
            ),
            correctIndex = 0,
            explanation = "O item da NR-01 que prevê o direito de recusa protege o trabalhador que interrompe tarefas que apresentem risco grave e iminente à sua vida."
        ),
        Question(
            id = 22,
            category = "Prevenção de Acidentes (CAT)",
            difficulty = Difficulty.DIFICIL,
            scenario = "Em uma quinta-feira às 14h, um assistente sofreu uma fratura no punho ao ser atingido por uma peça na linha de montagem.",
            prompt = "Qual é o prazo legal improrrogável para a empresa emitir a Comunicação de Acidente de Trabalho (CAT) perante a Previdência Social, caso não haja óbito?",
            options = listOf(
                "Até o primeiro dia útil seguinte ao da ocorrência e, em caso de morte, de imediato",
                "Até 30 dias corridos após o retorno do empregado ao serviço ativo",
                "Apenas quando o processo judicial for transitado em julgado",
                "Não há prazo obrigatório se a empresa pagar os remédios por conta própria"
            ),
            correctIndex = 0,
            explanation = "A CAT deve ser emitida até o primeiro dia útil seguinte ao acidente; em caso de morte, a comunicação às autoridades deve ser imediata."
        ),
        Question(
            id = 23,
            category = "NR-17 (Análise Ergonômica)",
            difficulty = Difficulty.DIFICIL,
            scenario = "Vários colaboradores da linha de embalagem foram afastados com diagnóstico de LER/DORT decorrente de movimentos repetitivos.",
            prompt = "Qual é o documento técnico aprofundado exigido pela NR-17 para avaliar situações de trabalho complexas que demandam intervenção ergonômica?",
            options = listOf(
                "Análise Ergonômica do Trabalho (AET)",
                "Apenas a Declaração de Vacinação em Dia",
                "Recibo de compra de tapetes antifadiga sem laudo",
                "Atestado de antecedentes criminais do operador de máquina"
            ),
            correctIndex = 0,
            explanation = "A AET (Análise Ergonômica do Trabalho) é o estudo detalhado que contempla a atividade real de trabalho, postura, esforço, cognição e organização."
        ),
        Question(
            id = 24,
            category = "Primeiros Socorros (RCP)",
            difficulty = Difficulty.DIFICIL,
            scenario = "Uma vítima de choque elétrico colapsa, não responde aos estímulos verbais e tácteis e não apresenta movimentos respiratórios (parada cardiorrespiratória).",
            prompt = "Qual deve ser a frequência recomendada de compressões torácicas por minuto segundo as diretrizes de RCP e Suporte Básico de Vida?",
            options = listOf(
                "100 a 120 compressões por minuto no centro do tórax, permitindo o retorno completo da caixa torácica",
                "30 compressões no abdômen uma vez a cada 5 minutos",
                "200 a 300 compressões ultrarrápidas sobre as costelas esquerdas",
                "Apenas respirações boca a boca sem qualquer compressão no peito"
            ),
            correctIndex = 0,
            explanation = "As diretrizes internacionais recomendam frequência de 100 a 120 compressões torácicas/min com profundidade de 5 a 6 cm no terço inferior do esterno."
        ),
        Question(
            id = 25,
            category = "NR-09 & Higiene Ocupacional",
            difficulty = Difficulty.DIFICIL,
            scenario = "Para quantificar a exposição dos trabalhadores ao calor proveniente de fornos industriais de fusão metálica, o técnico realiza medições.",
            prompt = "Qual índice métrico de sobrecarga térmica ocupacional é adotado na legislação brasileira e normas de higiene?",
            options = listOf(
                "Índice de Bulbo Úmido Termômetro de Globo (IBUTG)",
                "Sensação térmica informada pelo aplicativo de previsão do tempo do celular",
                "Grau Celsius medido por termômetro de mercúrio doméstico comum",
                "Temperatura ambiente estimada ao ar livre na calçada da fábrica"
            ),
            correctIndex = 0,
            explanation = "O IBUTG (Índice de Bulbo Úmido Termômetro de Globo) é o parâmetro oficial adotado para avaliação do estresse térmico ocupacional."
        ),
        Question(
            id = 26,
            category = "Investigação de Acidentes (Árvore de Causas)",
            difficulty = Difficulty.DIFICIL,
            scenario = "A equipe de SST do Senac investiga um rompimento de cabo em um guincho de carga que resultou em danos materiais.",
            prompt = "Ao aplicar o método da Árvore de Causas (ADC), qual é o critério rigoroso para a formulação dos fatos coletados?",
            options = listOf(
                "Registrar apenas fatos objetivos, concretos e verificáveis, evitando opiniões pessoais, juízos de valor e suposições",
                "Registrar opiniões anônimas e fofocas sobre o caráter do trabalhador",
                "Copiar o relatório de acidente de outra empresa ocorrido há 10 anos",
                "Atribuir a culpa antecipada ao funcionário mais novo do setor"
            ),
            correctIndex = 0,
            explanation = "O método da Árvore de Causas exige trabalhar estritamente com fatos comprovados e cronológicos, eliminando hipóteses infundadas e julgamentos."
        ),
        Question(
            id = 27,
            category = "NR-10 & Arco Elétrico",
            difficulty = Difficulty.DIFICIL,
            scenario = "Um técnico de manutenção atuará na substituição de chaves seccionadoras em uma subestação abrigada de 13,8 kV.",
            prompt = "Quais vestimentas e proteções são mandatórias na NR-10 para proteção contra os efeitos térmicos de um arco elétrico?",
            options = listOf(
                "Vestimentas de segurança com tecido antichama (tecnologia retardante de chamas/FR) compatíveis com a energia incidente estimada da instalação (cal/cm²)",
                "Camiseta de poliéster sintético 100% que derrete facilmente com calor",
                "Jaqueta de couro comum comprada em loja de motoqueiro sem ensaios elétricos",
                "Uniforme comum de algodão sem qualquer tratamento ou certificação térmica"
            ),
            correctIndex = 0,
            explanation = "A NR-10 exige vestimentas resistentes ao arco elétrico (tecido FR) e proíbe adornos e tecidos sintéticos combustíveis que possam queimar e aderir à pele."
        ),
        Question(
            id = 28,
            category = "NR-01 & Comunicação de Risco",
            difficulty = Difficulty.DIFICIL,
            scenario = "A empresa contratou uma prestadora de serviços terceirizada para executar manutenção de telhado durante o período letivo.",
            prompt = "Em relação ao PGR da contratante e da contratada, o que prescreve a NR-01 sobre a gestão integrada de riscos em estabelecimentos compartilhados?",
            options = listOf(
                "A organização contratante e as contratadas devem compartilhar informações sobre os perigos e riscos e coordenar as ações de prevenção",
                "A contratante não precisa saber nada sobre os riscos da contratada e vice-versa",
                "A responsabilidade recai integralmente sobre o porteiro do prédio",
                "As empresas são proibidas por lei de trocar dados sobre seus riscos de segurança"
            ),
            correctIndex = 0,
            explanation = "A NR-01 determina dever de cooperação e integração contínua do PGR entre empresa contratante e contratadas em mesmo ambiente de trabalho."
        ),
        Question(
            id = 29,
            category = "Cultura Coletiva de Segurança",
            difficulty = Difficulty.DIFICIL,
            scenario = "Um grupo de estudantes da turma M02 do Senac desenvolve uma campanha em que a participação de todos é pré-requisito para o sucesso.",
            prompt = "Por que o conceito da campanha 'Uma peça sozinha não completa o quebra-cabeça' representa a essência da Segurança do Trabalho?",
            options = listOf(
                "Porque a segurança não é responsabilidade de um único setor, e sim uma construção coletiva onde o cuidado mútuo preserva a vida de toda a comunidade",
                "Porque quem não joga o jogo deve ser multado pelo síndico do condomínio",
                "Porque a direção quer economizar na contratação de profissionais qualificados de SST",
                "Apenas para criar um trocadilho divertido sem fundamentação técnica de engenharia de segurança"
            ),
            correctIndex = 0,
            explanation = "A Segurança do Trabalho é uma construção diária e coletiva: gestores, técnicos, operadores e estudantes formam o mosaico completo de prevenção."
        ),
        Question(
            id = 30,
            category = "Investigação & Causa Raiz",
            difficulty = Difficulty.DIFICIL,
            scenario = "Durante a conclusão da SPAT Quebra-Cabeça, os investigadores identificam que o acidente no laboratório foi antecedido por múltiplos alertas desconsiderados.",
            prompt = "Qual é a principal lição da técnica dos '5 Porquês' (5 Whys) aplicada à prevenção definitiva de acidentes?",
            options = listOf(
                "Ir além dos sintomas imediatos e desvios superficiais para atingir a causa-raiz no sistema de gestão organizacional",
                "Fazer 5 perguntas ao acidentado até que ele confesse sua culpa por escrito",
                "Preencher 5 formulários iguais e encaminhá-los para 5 secretarias diferentes",
                "Reduzir o tempo de treinamento de segurança para apenas 5 minutos anuais"
            ),
            correctIndex = 0,
            explanation = "A metodologia dos 5 Porquês investiga as causas sucessivas até alcançar a causa-raiz estrutural, impedindo a reincidência do evento indesejado."
        )
    )
}
