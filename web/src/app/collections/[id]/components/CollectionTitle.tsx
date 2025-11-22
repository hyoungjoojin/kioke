'use client';

import useGetCollectionByIdQuery from '@/hooks/query/useGetCollectionByIdQuery';

interface CollectionTitleProps {
  collectionId: string;
}

function CollectionTitle({ collectionId }: CollectionTitleProps) {
  const { data: collection } = useGetCollectionByIdQuery({
    path: { collectionId },
  });

  if (!collection) {
    return null;
  }

  return (
    <>
      <h1 className='text-2xl'>{collection.name}</h1>
    </>
  );
}

export default CollectionTitle;
