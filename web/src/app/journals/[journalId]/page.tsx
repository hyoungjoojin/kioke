import JournalContent from './components/JournalContent';
import JournalTitle from './components/JournalTitle';

export default async function Journal({
  params,
}: {
  params: Promise<{ journalId: string }>;
}) {
  const { journalId } = await params;

  return (
    <>
      <JournalTitle journalId={journalId} />
      <JournalContent journalId={journalId} />
    </>
  );
}
