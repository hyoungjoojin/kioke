import { GetDefaultShelfResponse } from "@/types/api/shelf";
import { NextResponse } from "next/server";

export async function GET(): Promise<NextResponse<GetDefaultShelfResponse>> {
  return NextResponse.json(
    {
      id: "dummy_id",
    },
    {
      status: 200,
    },
  );
}
