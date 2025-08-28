import type { SingleJournalWidgetContent } from '.';
import { Spinner } from '@/components/ui/spinner';
import { useJournalQuery } from '@/query/journal';

export default function SingleJournalWidget({
  journalId = '',
}: SingleJournalWidgetContent) {
  const {
    data: journal,
    error: journalQueryError,
    isPending: isJournalQueryPending,
    isError: isJournalQueryError,
  } = useJournalQuery(
    {
      journalId,
    },
    {
      enabled: journalId !== '',
    },
  );

  if (isJournalQueryError) {
    if (!journalQueryError) {
      return null;
    }

    return <div>Journal Not Found</div>;
  }

  return isJournalQueryPending ? (
    <Spinner />
  ) : (
    journal && <div>{journal.title}</div>
  );
}
