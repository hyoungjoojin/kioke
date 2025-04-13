package kioke.journal.dto.request.journal;

public record UpdateJournalRequestBodyDto(
    String shelfId, String title, String description, Boolean bookmark) {}
