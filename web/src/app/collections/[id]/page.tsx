import CollectionTitle from './components/CollectionTitle';
import Journals from './components/Journals';
import { Separator } from '@/components/ui/separator';
import { UseGetCollectionByIdQueryDefaultOptions } from '@/hooks/query/useGetCollectionByIdQuery';
import { getQueryClient } from '@/lib/query';
import { handleError } from '@/util/error';
import { HydrationBoundary, dehydrate } from '@tanstack/react-query';

interface CollectionProps {
  params: Promise<{
    id: string;
  }>;
}

async function Collection({ params }: CollectionProps) {
  const { id } = await params;

  const queryClient = getQueryClient();
  await queryClient
    .fetchQuery(
      UseGetCollectionByIdQueryDefaultOptions({ path: { collectionId: id } }),
    )
    .catch((error) => handleError(error));

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <CollectionTitle collectionId={id} />
      <Separator className='mt-2' />
      <Journals collectionId={id} />
    </HydrationBoundary>
  );
}

export default Collection;
