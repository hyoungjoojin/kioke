"use client";

import { Skeleton } from "@/components/ui/skeleton";
import { useShelvesQuery } from "@/hooks/query";
import { useGetSelectedShelf } from "@/hooks/store";

const ShelfHeaderSkeleton = () => {
  return <Skeleton className="h-12 w-64 my-10 rounded-lg" />;
};

export default function ShelfHeader() {
  const { data: getShelvesResponse, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useGetSelectedShelf(getShelvesResponse?.shelves);

  if (isLoading || isError) {
    return <ShelfHeaderSkeleton />;
  }

  return selectedShelf ? (
    <div className="h-16 my-10 flex justify-center items-center">
      <h1 className="text-3xl">{selectedShelf.name}</h1>
    </div>
  ) : (
    <ShelfHeaderSkeleton />
  );
}
