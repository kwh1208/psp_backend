package hwagae.psp.service;

import hwagae.psp.dto.request.RequestAnswerDto;
import hwagae.psp.dto.request.RequestAnswerListDto;
import hwagae.psp.dto.request.RequestWorkbookDto;
import hwagae.psp.dto.response.ResponseAnswerDto;
import hwagae.psp.dto.response.ResponseWorkbookDto;
import hwagae.psp.entity.Problem;
import hwagae.psp.entity.Workbook;
import hwagae.psp.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkbookService {

    private final WorkbookRepository workbookRepository;
    private final ProblemService problemService;

    public void saveWorkbook(RequestWorkbookDto workbook) {
        Workbook saveWorkbook = workbook.toEntity();
        Workbook saved = workbookRepository.save(saveWorkbook);
        List<Problem> problemList
                = workbook.getProblemList().stream().map(p -> problemService.saveProblem(p, saved)).toList();

        saveWorkbook.setProblemList(problemList);
    }

    public ResponseWorkbookDto findById(Long id) {
        Optional<Workbook> optionalWorkbook = workbookRepository.findById(id);
        if(optionalWorkbook.isEmpty())
            throw new RuntimeException();

        Workbook workbook = optionalWorkbook.get();
        return new ResponseWorkbookDto(workbook);
    }

    public ResponseAnswerDto solvedProblems(RequestAnswerListDto answerListDto) {
        int rightCnt = 0;
        int wrongCnt = 0;
        ArrayList<Long> rightProblemNum = new ArrayList<>();
        ArrayList<Long> wrongProblemNum = new ArrayList<>();

        for(RequestAnswerDto answer : answerListDto.getAnswerList()) {
            if(problemService.solved(answer)) {
                rightCnt += 1;
                rightProblemNum.add(answer.getProblemId());
            }
            else {
                wrongCnt+=1;
                wrongProblemNum.add(answer.getProblemId());
            }
        }

        return ResponseAnswerDto.builder()
                .rightCount(rightCnt)
                .wrongCount(wrongCnt)
                .rightProblemList(rightProblemNum)
                .wrongProblemList(wrongProblemNum)
                .build();
    }
}
