package hwagae.psp.controller;

import hwagae.psp.dto.request.RequestAnswerListDto;
import hwagae.psp.dto.request.RequestWorkbookDto;
import hwagae.psp.dto.response.ResponseAnswerDto;
import hwagae.psp.dto.response.ResponseWorkbookDto;
import hwagae.psp.service.WorkbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workbook")
@RequiredArgsConstructor
public class WorkbookApiController {

    private final WorkbookService workbookService;

    @PostMapping("/save")
    public ResponseEntity<String> saveWorkbook(@RequestBody RequestWorkbookDto workbookDto) {
        workbookService.saveWorkbook(workbookDto);
        return ResponseEntity.ok("저장 완료");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWorkbookDto> getWorkbook(@PathVariable Long id) {
        ResponseWorkbookDto workbookDto = workbookService.findById(id);
        return ResponseEntity.ok(workbookDto);
    }


    /**
     * 문제집 정답 채점
     */
    @PostMapping("/answer/submit")
    public ResponseEntity<ResponseAnswerDto> solvedWorkbook(@RequestBody RequestAnswerListDto answerListDto) {
        ResponseAnswerDto solvedProblems = workbookService.solvedProblems(answerListDto);
        return ResponseEntity.ok(solvedProblems);
    }
}
