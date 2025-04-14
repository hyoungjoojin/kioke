import type { NextConfig } from "next";
import createNextIntlPlugin from "next-intl/plugin";

const withNextIntl = createNextIntlPlugin("./src/lib/i18n/request.ts");

const nextConfig: NextConfig = {
  experimental: {
    serverActions: {
      allowedOrigins: [
        "localhost:3000",
        "sturdy-doodle-wr774jpjg7r9h5qw4-3000.app.github.dev",
      ],
    },
  },
};

export default withNextIntl(nextConfig);
