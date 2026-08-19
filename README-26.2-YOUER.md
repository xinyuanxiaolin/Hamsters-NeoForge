# Hamsters 26.2 Youer 移植说明

- 目标版本：Minecraft 26.2、NeoForge 26.2.0.59、Java 25。
- 移除旧版 Create、MidnightLib 与 GeckoLib 的强制依赖，降低 Youer 混合端加载冲突。
- 保留原仓库全部 52 个方块 ID 与 20 个普通物品 ID，旧世界和资源包可继续识别。
- 仓鼠实体改用 26.2 原版兔类生物逻辑，支持自然生成、繁殖与基础 AI。
- 生成范围覆盖原版温和陆地群系，并兼容 BOP、BYG 和 YoakeBiomes 的适宜群系。
- 排除冰原、极寒山峰、海洋、下界与末地的显式生成配置。

当前 26.2 分支优先保证服务器稳定、实体生成和注册表兼容；旧版 GeckoLib 专属动画与 Create 动力输出不在此兼容构建中启用。
