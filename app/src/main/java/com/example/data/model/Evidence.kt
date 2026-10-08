package com.example.data.model

import com.example.R

data class Evidence(
    val id: Int,
    val title: String,
    val subtitle: String,
    val word: String, // Strict Campaign word
    val pieceIndex: Int,
    val narrative: String,
    val inspectionPrompt: String,
    val inspectionQuestion: String,
    val inspectionOptions: List<String>,
    val correctInspectionOptionIndex: Int,
    val nrInformation: String,
    val imageResId: Int
)

object EvidenceData {
    val OFFICIAL_PHRASE_WORDS = listOf("JOGANDO", "JUNTOS", "CONSTRUÍMOS", "NOSSA", "SEGURANÇA")
    const val OFFICIAL_FULL_PHRASE = "JOGANDO JUNTOS, CONSTRUÍMOS NOSSA SEGURANÇA."

    val evidences = listOf(
        Evidence(
            id = 1,
            title = "EVIDÊNCIA 01",
            subtitle = "O LOCAL",
            word = "JOGANDO",
            pieceIndex = 1,
            narrative = "No piso da oficina técnica do Senac Vila Prudente, a equipe pericial de SST isolou uma área com derramamento recente de substância escorregadia e falta de sinalização preventiva zebrada.",
            inspectionPrompt = "Examine a cena do acidente utilizando a lupa pericial e analise o fator de risco predominante no ambiente físico.",
            inspectionQuestion = "Ao chegar ao local de um acidente com derramamento de líquido, qual é a conduta primária de SST?",
            inspectionOptions = listOf(
                "Isolar imediatamente a área para evitar novos sinistros e iniciar a sinalização",
                "Continuar as atividades ao redor enquanto o setor de limpeza não chega",
                "Fotografar para redes sociais antes de avisar os responsáveis",
                "Cobrir com papéis de rascunho sem sinalizar o desnível ou piso escorregadio"
            ),
            correctInspectionOptionIndex = 0,
            nrInformation = "NR-01 (GRO/PGR): O gerenciamento de riscos ocupacionais exige identificação imediata de perigos no ambiente de trabalho e adoção de medidas preventivas de contenção antes de qualquer atividade.",
            imageResId = R.drawable.evidence_local_1789603550846
        ),
        Evidence(
            id = 2,
            title = "EVIDÊNCIA 02",
            subtitle = "A CONDIÇÃO",
            word = "JUNTOS",
            pieceIndex = 2,
            narrative = "Próximo à bancada de comando, o painel elétrico de força apresentava fiação exposta e a trava de proteção mecânica da prensa havia sido desativada temporariamente sem permissão de trabalho.",
            inspectionPrompt = "Investigue a condição insegura das instalações e equipamentos presentes no posto de trabalho.",
            inspectionQuestion = "Diante de um equipamento com dispositivo de segurança inoperante ou painel aberto, qual a determinação das NRs?",
            inspectionOptions = listOf(
                "Operar com cuidado redobrado até o fim do expediente",
                "Desligar, bloquear com cadeado/etiqueta (LOTO) e comunicar a liderança de SST",
                "Amarrar a chave com fita adesiva para que não desarme",
                "Pedir para um colega segurar o painel enquanto o outro opera a máquina"
            ),
            correctInspectionOptionIndex = 1,
            nrInformation = "NR-10 & NR-12: Sistemas de bloqueio e consignação de energias perigosas (LOTO) e proteções físicas são inegociáveis. Máquina sem proteção adequada deve ser imediatamente paralisada.",
            imageResId = R.drawable.evidence_condicao_1789603561527
        ),
        Evidence(
            id = 3,
            title = "EVIDÊNCIA 03",
            subtitle = "O COMPORTAMENTO",
            word = "CONSTRUÍMOS",
            pieceIndex = 3,
            narrative = "A prancheta de relatos de quase-acidentes estava vazia. Entrevistas revelaram que colaboradores notavam desvios rotineiros mas não registravam por pressa ou medo de cobrança.",
            inspectionPrompt = "Analise o relatório investigativo e a cultura de relato preventivo na unidade de trabalho.",
            inspectionQuestion = "O que caracteriza a construção de uma cultura de comportamento seguro coletivo nas empresas?",
            inspectionOptions = listOf(
                "Culpar o operador que cometeu o desvio e aplicar advertência pública",
                "Incentivar o relato sem punição, investigação da causa-raiz e participação da CIPA",
                "Ignorar pequenos incidentes e focar apenas em acidentes com afastamento grave",
                "Substituir todos os funcionários que apontam falhas estruturais"
            ),
            correctInspectionOptionIndex = 1,
            nrInformation = "NR-05 (CIPA) & Investigação de Acidentes: Segurança do Trabalho é uma construção participativa. A cultura justa prioriza aprender com os desvios e eliminar as causas fundamentais em equipe.",
            imageResId = R.drawable.evidence_comportamento_1789603572996
        ),
        Evidence(
            id = 4,
            title = "EVIDÊNCIA 04",
            subtitle = "O EQUIPAMENTO",
            word = "NOSSA",
            pieceIndex = 4,
            narrative = "Sobre a bancada pericial foram encontrados óculos de proteção com lentes trincadas e luvas de vaqueta desgastadas sem Certificado de Aprovação (CA) legível.",
            inspectionPrompt = "Pericie os Equipamentos de Proteção Individual (EPIs) recolhidos no perímetro do acidente.",
            inspectionQuestion = "Segundo a NR-06, qual é a responsabilidade compartilhada sobre o EPI no ambiente laboral?",
            inspectionOptions = listOf(
                "O empregado deve comprar seus próprios EPIs e higienizá-los em casa",
                "O empregador fornece gratuitamente com CA válido, e o empregado tem o dever de usá-lo e zelar",
                "O EPI é opcional quando o trabalhador possui mais de 10 anos de experiência prática",
                "Pode-se utilizar EPI danificado desde que a tarefa dure menos de 15 minutos"
            ),
            correctInspectionOptionIndex = 1,
            nrInformation = "NR-06 (EPI): O empregador fornece gratuitamente o equipamento adequado com CA válido; o trabalhador é corresponsável pelo uso obrigatório, guarda, conservação e comunicação de danos.",
            imageResId = R.drawable.evidence_equipamento_1789603582394
        ),
        Evidence(
            id = 5,
            title = "EVIDÊNCIA 05",
            subtitle = "A PISTA FINAL",
            word = "SEGURANÇA",
            pieceIndex = 5,
            narrative = "O mapa de risco da área foi cruzado com os dados do acidente. Descobriu-se que a falha não foi individual: faltou alinhamento contínuo entre procedimentos e comunicação coletiva.",
            inspectionPrompt = "Conecte os dados periciais finais para desvendar a mensagem oculta da campanha SPAT.",
            inspectionQuestion = "Qual é o princípio fundamental da investigação pericial moderna de Segurança do Trabalho?",
            inspectionOptions = listOf(
                "Identificar falhas no sistema de gestão e proteger a vida de todos como um time",
                "Descobrir um único culpado para encerrar o inquérito policial",
                "Esconder os laudos da CIPA para preservar a imagem comercial da instituição",
                "Arquivar o caso sem propor planos de ação corretiva ou preventiva"
            ),
            correctInspectionOptionIndex = 0,
            nrInformation = "Princípio de SST: Uma peça sozinha não completa o quebra-cabeça. A segurança depende da união e vigilância de cada trabalhador, aluno e gestor. Jogando juntos, construímos nossa segurança!",
            imageResId = R.drawable.evidence_final_1789603593120
        )
    )
}
