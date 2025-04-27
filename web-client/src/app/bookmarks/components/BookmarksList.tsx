'use client';

import { useGetBookmarkedJournals } from '@/hooks/query/journal';

export default function BookmarksList() {
  const { data: getJournalsResponse, isLoading } = useGetBookmarkedJournals();

  if (isLoading || getJournalsResponse === undefined) {
    return null;
  }

  const journals = getJournalsResponse.journals;

  return (
    <div>
      {journals &&
        journals.map((journal, index) => {
          return <div key={index}>{journal.title}</div>;
        })}
    </div>
  );
}
