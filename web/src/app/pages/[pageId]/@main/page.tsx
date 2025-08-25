import PageEditor from './components/PageEditor';
import PageTitle from './components/PageTitle';

export default async function Page({
  params,
}: {
  params: Promise<{ pageId: string }>;
}) {
  const { pageId } = await params;

  return (
    <>
      <PageTitle />
      <PageEditor pageId={pageId} />
    </>
  );
}
