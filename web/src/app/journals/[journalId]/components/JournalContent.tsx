'use client';

import CreatePageButton from './CreatePageButton';
import { BasicJournalTypeContent, ShortJournalTypeContent } from './content';
import { JournalType } from '@/constant/journal';
import { useJournalQuery } from '@/query/journal';

interface JournalContentProps {
  journalId: string;
}

export default function JournalContent({ journalId }: JournalContentProps) {
  const { data: journal, isPending: isJournalPending } = useJournalQuery({
    journalId,
  });

  if (isJournalPending) {
    return null;
  }

  if (!journal) {
    return null;
  }

  function content(type: JournalType) {
    switch (type) {
      case JournalType.BASIC:
        return <BasicJournalTypeContent journalId={journalId} />;

      case JournalType.SHORT:
        return <ShortJournalTypeContent journalId={journalId} />;
    }
  }

  return (
    <div className='flex flex-col'>
      <div className='self-end'>
        <CreatePageButton journalId={journalId} />
      </div>

      {content(journal.type)}
    </div>
  );
}
