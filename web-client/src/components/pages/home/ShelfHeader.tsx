"use client";

import { Skeleton } from "@/components/ui/skeleton";
import { useShelvesQuery } from "@/hooks/query";
import { useSelectedShelf } from "@/hooks/store";

const ShelfHeaderSkeleton = () => {
  return <Skeleton className="h-12 w-64 my-10 rounded-lg" />;
};

export default function ShelfHeader() {
  const { data, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(data?.shelves);

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
